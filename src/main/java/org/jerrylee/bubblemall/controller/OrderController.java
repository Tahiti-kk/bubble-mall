package org.jerrylee.bubblemall.controller;

import io.swagger.annotations.Api;
import org.jerrylee.bubblemall.controller.viewobject.ItemVO;
import org.jerrylee.bubblemall.error.BusinessException;
import org.jerrylee.bubblemall.error.EmBusinessError;
import org.jerrylee.bubblemall.response.CommonReturnType;
import org.jerrylee.bubblemall.service.ItemService;
import org.jerrylee.bubblemall.service.OrderService;
import org.jerrylee.bubblemall.service.model.ItemModel;
import org.jerrylee.bubblemall.service.model.OrderModel;
import org.jerrylee.bubblemall.service.model.UserModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.jerrylee.bubblemall.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * @author JerryLee
 * @date 2020/4/28
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@Api(tags = "OrderController | 订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    // 封装下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }

        //获取用户的登陆信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        return CommonReturnType.create(null);
    }

}
