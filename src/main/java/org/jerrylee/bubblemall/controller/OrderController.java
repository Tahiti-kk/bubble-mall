package org.jerrylee.bubblemall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author JerryLee
 * @date 2020/4/28
 */
@Controller("order")
@RequestMapping("/order")
@Api(tags = "OrderController | 订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 封装下单请求
     */
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "用户下单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户令牌", dataType = "String", required = true),
            @ApiImplicitParam(name = "itemId", value = "商品id", dataType = "int", required = true),
            @ApiImplicitParam(name = "amount", value = "商品数量", dataType = "int", required = true),
            @ApiImplicitParam(name = "promoId", value = "促销活动id", dataType = "int", required = false)
    })
    public CommonReturnType createOrder(@RequestParam(name="token")String token,
                                        @RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId", required = false)Integer promoId) throws BusinessException {

        // 获取用户的登陆信息
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);

        if(userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户未登录，请先登录");
        }
        // 更新token时间
        String uuidToken = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.delete(token);
        redisTemplate.opsForValue().set(uuidToken, userModel);
        redisTemplate.expire(uuidToken, 1, TimeUnit.HOURS);

        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        return CommonReturnType.create(null);
    }

}
