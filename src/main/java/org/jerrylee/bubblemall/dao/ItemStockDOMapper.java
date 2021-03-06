package org.jerrylee.bubblemall.dao;

import org.apache.ibatis.annotations.Param;
import org.jerrylee.bubblemall.dataobject.ItemStockDO;

/**
 * 新增方法:
 * selectByItemId
 * decreaseStock
 */
public interface ItemStockDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    int insert(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    int insertSelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    ItemStockDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    int updateByPrimaryKeySelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Mon Apr 27 21:09:49 CST 2020
     */
    int updateByPrimaryKey(ItemStockDO record);

    ItemStockDO selectByItemId(Integer itemId);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}