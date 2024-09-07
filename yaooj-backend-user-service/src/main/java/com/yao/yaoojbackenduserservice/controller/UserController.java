package com.yao.yaoojbackenduserservice.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.yao.yaoojbackendcommon.annotation.AuthCheck;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.common.Result;
import com.yao.yaoojbackendcommon.constant.UserConstant;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendmodel.dto.*;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.UserLoginVO;
import com.yao.yaoojbackendmodel.vo.UserVO;
import com.yao.yaoojbackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * ClassName: UserController
 * Package: com.yao.yaoojbackendmy.controller
 * Description:
 * 用户接口
 *
 * @Author Joy_瑶
 * @Create 2024/7/17 19:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    // region 登录相关
    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody UserRegisterDTO userRegisterDTO){
        if(userRegisterDTO==null){
            return Result.error(ErrorCode.PARAMS_ERROR);
        }
        long userId=userService.userRegister(userRegisterDTO);
        return Result.success(userId);
    }
    /**
     * 用户登录
     * @param userLoginDTO
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("调用登录用户请求：{}",request);
        if(userLoginDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginDTO.getUserAccount();
        String userPassword = userLoginDTO.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserLoginVO userLoginVO=userService.userLogin(userAccount,userPassword,request);
        return Result.success(userLoginVO);
    }

    /**
     * 获取登录用户信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/get/login")
    public Result<UserLoginVO> getLoginUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        User user=userService.getLoginUser(request);
        return Result.success(userService.getLoginUserVO(user));
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<Boolean> userLogout(HttpServletRequest request){
        if(request==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result=userService.userLogout(request);
        return Result.success(result);
    }
    // endregion

    //region 增删改查

    /**
     * 创建用户(管理员)
     * @param userAddDTO
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Long> addUser(@RequestBody UserAddDTO userAddDTO, HttpServletRequest request){
        //返回用户id
        if(userAddDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userAddDTO,user);
        boolean result = userService.save(user);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return Result.success(user.getId());
    }

    /**
     * 删除用户（管理员）
     * @param userDeleteDTO
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> deleteUser(@RequestBody UserDeleteDTO userDeleteDTO, HttpServletRequest request){
        if(userDeleteDTO==null||userDeleteDTO.getId()==null||userDeleteDTO.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = userDeleteDTO.getId();
        User oldUser = userService.getById(id);
        if(oldUser==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"待删除用户不存在");
        }
        boolean b = userService.removeById(userDeleteDTO.getId());
        return Result.success(b);
    }

    /**
     * 更新用户（管理员）
     *
     * @param userUpdateDTO
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, HttpServletRequest request){
        if(userUpdateDTO==null||userUpdateDTO.getId()==null||userUpdateDTO.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateDTO,user);
        boolean result = userService.updateById(user);
        if(!result){
            throw  new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 根据id查询用户（管理员）
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<User> getUserById(long id,HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        if(user==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return Result.success(user);
    }

    /**
     * 根据id获取用户包装类
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/vo")
    public Result<UserVO> getUserVOById(long id, HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Result<User> response = getUserById(id,request);
        User user = response.getData();
        return Result.success(userService.getUserVO(user));
    }


    /**
     * 分页获取用户列表（仅管理员）
     * @param userQueryDTO
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Page<User>> listUserByPage(@RequestBody UserQueryDTO userQueryDTO,HttpServletRequest request){
        if(userQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不能为空！");
        }
        long current = userQueryDTO.getCurrent();
        long size = userQueryDTO.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryDTO));
        return Result.success(userPage);
    }


    /**
     * 分页获取用户封装列表
     * @param userQueryDTO
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public Result<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryDTO userQueryDTO,HttpServletRequest request){
        if(userQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数不能为空！");
        }
        long current = userQueryDTO.getCurrent();
        long size = userQueryDTO.getPageSize();
        //限制爬虫：每一页的数据不能超过20条
        if(size>20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求数据量过多！");
        }
        Page<User> userPage = userService.page(new Page<User>(current, size),
                userService.getQueryWrapper(userQueryDTO));
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        userVOPage.setRecords(userVO);
        return Result.success(userVOPage);
    }

    //endregion
    @PostMapping("/update/my")
    public Result<Boolean> updateMyUser(@RequestBody UserUpdateMyDTO userUpdateMyDTO, HttpServletRequest request){
        if(userUpdateMyDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
//        User loginUser = UserHolder.getUser();
        User user = new User();
        BeanUtil.copyProperties(userUpdateMyDTO,user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        if(!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return Result.success(true);
    }

}
