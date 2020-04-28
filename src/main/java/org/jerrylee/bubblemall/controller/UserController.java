package org.jerrylee.bubblemall.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.jerrylee.bubblemall.controller.viewobject.UserVO;
import org.jerrylee.bubblemall.error.BusinessException;
import org.jerrylee.bubblemall.error.EmBusinessError;
import org.jerrylee.bubblemall.response.CommonReturnType;
import org.jerrylee.bubblemall.service.UserService;
import org.jerrylee.bubblemall.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author JerryLee
 * @date 2020/4/23
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@Api(tags = "UserController | 用户接口")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/get")
    @ResponseBody
    @ApiOperation(value = "根据用户id获得信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true)
    })
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {

        // 调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        // 若获取的对应用户信息不存在，则抛出异常
        if(userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        // 将用户model对象转化为供UI使用的viewobject
        UserVO userVO  = convertFromModel(userModel);

        // 返回通用对象
        return CommonReturnType.create(userVO);
    }

    /**
     * 用户获取otp短信接口
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    @ApiOperation(value = "根据手机号获得验证码", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", dataType = "String", required = true)
    })
    public CommonReturnType getOtp(@RequestParam(name="telephone")String telephone){
        // 生成OTP验证码
        Random random = new Random();
        int randomInt =  random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        // 将OTP验证码同对应用户的手机号关联，使用httpsession的方式绑定手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telephone,otpCode);

        // 将OTP验证码通过短信通道发送给用户,此处省略
        System.out.println("telephone = " + telephone + " & otpCode = "+otpCode);

        return CommonReturnType.create(null);
    }

    /**
     * 用户注册接口
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", dataType = "String", required = true),
            @ApiImplicitParam(name = "otpCode", value = "验证码", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = true),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "int", required = true),
            @ApiImplicitParam(name = "age", value = "年龄", dataType = "int", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)
    })
    public CommonReturnType register(@RequestParam(name="telephone")String telephone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, NoSuchAlgorithmException {
        // 验证手机号和对应的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"验证码错误");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");
        userModel.setEncryptPassword(encodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    /**
     * 用户登陆接口
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)
    })
    public CommonReturnType login(@RequestParam(name="telephone")String telephone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        // 参数校验
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登陆服务,用来校验用户登陆是否合法
        UserModel userModel = userService.validateLogin(telephone, encodeByMd5(password));

        // 将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);
    }

    /**
     * 使用md5加密密码
     */
    public static String encodeByMd5(String str) throws NoSuchAlgorithmException {
        // 新建md5对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        // 加密密码
        md5.update(str.getBytes());

        return new BigInteger(1, md5.digest()).toString(16);
    }

    /**
     * 将model对象转换成viewobject对象
     */
    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
