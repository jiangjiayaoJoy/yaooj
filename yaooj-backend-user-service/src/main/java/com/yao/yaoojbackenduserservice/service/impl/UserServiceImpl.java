package com.yao.yaoojbackenduserservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.constant.CommonConstant;
import com.yao.yaoojbackendcommon.constant.UserConstant;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendcommon.utils.SqlUtils;
import com.yao.yaoojbackendmodel.dto.UserQueryDTO;
import com.yao.yaoojbackendmodel.dto.UserRegisterDTO;
import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendmodel.vo.UserLoginVO;
import com.yao.yaoojbackendmodel.vo.UserVO;
import com.yao.yaoojbackenduserservice.mapper.UserMapper;
import com.yao.yaoojbackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yao.yaoojbackendcommon.constant.UserConstant.USER_LOGIN_STATUS;


/**
 * ClassName: UserServiceImpl
 * Package: com.yao.yaoojbackendmy.service.impl
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/18 21:45
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    /**
     * 盐值，混淆密码
     */
    public static final String SALT="yaojia";

    @Override
    public long userRegister(UserRegisterDTO userRegisterDTO) {
        //1.校验
        String userAccount = userRegisterDTO.getUserAccount();
        String userPassword = userRegisterDTO.getUserPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if(userPassword.length()<8||checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        //密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }

        //根据userAccount查询数据库，不能出现相同的userAccount
        List<User> userList=userMapper.selectByuserAccount(userAccount);
        if(userList.size()>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        //2.对密码进行md5混淆加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        this.save(user);
        //将数据插入user表（使用mapper+xml）
//        userMapper.insert(user);
        return user.getId();
    }

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public UserLoginVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //用户登录的逻辑：校验登录信息，检查用户是否存在，保存登陆状态到session，返回脱敏之后的用户信息
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //用户名长度不能小于4
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于4位");
        }
        //密码长度不能小于8
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8位");
        }

        //2.查询用户是否存在
        //根据账号和密码（md5加密后的）进行查询
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq("userAccount",userAccount)
                .eq("userPassword",userPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            log.info("用户登陆失败，账号不存在会密码错误");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不存在会密码错误");
        }

        //3.保存用户的登录状态
        //todo 将用户的登录状态保存到redis中
        request.getSession().setAttribute(USER_LOGIN_STATUS,user);

        return getLoginUserVO(user);
    }



    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        //用户注销的逻辑不是将用户从数据库中删除，而是删除用户的登录状态！
        //所以将用户的session删除即可
        if(request.getSession().getAttribute(USER_LOGIN_STATUS)==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"未登录");
        }
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return true;
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    @Override
    public UserLoginVO getLoginUserVO(User user) {
        if(user==null){
            return null;
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtil.copyProperties(user,userLoginVO);
        return userLoginVO;
    }

    /**
     * 获取脱敏用户信息
     * @param user
     * @return
     */
    @Override
    public UserVO getUserVO(User user) {
        if(user==null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return userVO;
    }

    /**
     * 获取脱敏用户信息列表
     * @param userList
     * @return
     */
    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if(CollectionUtils.isEmpty(userList)){
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser=(User)userObj;
        if(currentUser==null||currentUser.getId()==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user=(User)userObj;
        return isAdmin(user);
    }

    /**
     * 是否为管理员
     * @param user
     * @return
     */
    @Override
    public boolean isAdmin(User user) {
        return user!=null&&user.getUserRole().equals(UserConstant.ADMIN_ROLE);
    }

    /**
     * 获取查询用户条件
     * @param userQueryDTO
     * @return
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryDTO) {
        if(userQueryDTO==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空！");
        }

        Long id = userQueryDTO.getId();
        String unionId = userQueryDTO.getUnionId();
        String mpOpenId = userQueryDTO.getMpOpenId();
        String userName = userQueryDTO.getUserName();
        String userProfile = userQueryDTO.getUserProfile();
        String userRole = userQueryDTO.getUserRole();
        String sortField = userQueryDTO.getSortField();
        String sortOrder = userQueryDTO.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=null,"id",id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile),"userProfile",userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName),"userName",userName);
        //因为排序字段是将用户输入的参数直接拼接到sql语句中的(order by xxx)，所以需要防止sql注入！
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);

        return queryWrapper;
    }
}
