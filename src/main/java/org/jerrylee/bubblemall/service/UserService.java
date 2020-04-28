package org.jerrylee.bubblemall.service;

import org.jerrylee.bubblemall.error.BusinessException;
import org.jerrylee.bubblemall.service.model.UserModel;

/**
 * @author JerryLee
 * @date 2020/4/23
 */
public interface UserService {
    /**
     * 通过用户id获取用户对象
     */
    UserModel getUserById(Integer id);

    /**
     * 用户注册
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户校验
     * @param telephone 用户手机号
     * @param encryptPassword 加密密码
     */
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;
}
