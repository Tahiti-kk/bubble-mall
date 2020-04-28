package org.jerrylee.bubblemall.service.model;

import java.math.BigDecimal;

/**
 * @author JerryLee
 * @date 2020/4/27
 */
public class OrderModel {
    /**
     * 注意此处的id号为String
     */
    private String id;

    private Integer userId;
    private Integer itemId;

    /**
     * 促销活动id
     * 若为空，则表明不是促销商品
     */
    private Integer promoId;

    private BigDecimal itemPrice;
    private Integer amount;
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
