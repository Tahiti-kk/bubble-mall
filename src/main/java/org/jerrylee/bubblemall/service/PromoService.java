package org.jerrylee.bubblemall.service;

import org.jerrylee.bubblemall.service.model.PromoModel;

/**
 * @author JerryLee
 * @date 2020/4/27
 */
public interface PromoService {
    /**
     * 根据itemId获取即将进行的或正在进行的秒杀活动
     */
    PromoModel getPromoByItemId(Integer itemId);
}
