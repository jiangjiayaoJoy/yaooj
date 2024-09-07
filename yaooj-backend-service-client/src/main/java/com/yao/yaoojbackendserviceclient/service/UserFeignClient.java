package com.yao.yaoojbackendserviceclient.service;




import cn.hutool.core.bean.BeanUtil;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.enums.UserRoleEnum;
import com.yao.yaoojbackendmodel.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.yao.yaoojbackendcommon.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * ClassName: UserService
 * Package: com.yao.yaoojbackendserviceclient.service
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/9 17:19
 * @Version 1.0
 */
@FeignClient(name = "yaooj-backend-user-service",path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据 id 获取用户
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);


    /**
     * 根据 id 获取用户列表
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request){
        //先判断是否登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser=(User)userObj;
        if(currentUser==null||currentUser.getId()==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //可以考虑在这里做全局权限校验
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user){
        return user!=null&&UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    //    userService.getUserVO(user)
    default UserVO getUserVO(User user){
        if(user==null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return userVO;
    }
}
