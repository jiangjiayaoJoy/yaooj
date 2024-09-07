package com.yao.yaoojbackenduserservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.yaoojbackendmodel.dto.UserQueryDTO;
import com.yao.yaoojbackendmodel.dto.UserRegisterDTO;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.UserLoginVO;
import com.yao.yaoojbackendmodel.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: UserService
 * Package: com.yao.yaoojbackendmy.service
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/18 21:44
 * @Version 1.0
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    long userRegister(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏之后的用户信息
     */
    UserLoginVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);
    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserLoginVO getLoginUserVO(User user);

    /**
     * 获取脱敏用户信息
     * @param user
     * @return
     */
    UserVO getUserVO(User user);
    /**
     * 获取脱敏用户信息列表
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 获取查询用户条件
     * @param userQueryDTO
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryDTO);
}
