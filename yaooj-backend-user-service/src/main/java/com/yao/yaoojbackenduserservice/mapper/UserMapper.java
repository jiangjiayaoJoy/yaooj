package com.yao.yaoojbackenduserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.yaoojbackendmodel.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: UserMapper
 * Package: com.yao.yaoojbackendmy.mapper
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/19 11:01
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据userAccount查询用户
     * @param userAccount
     * @return
     */
    @Select("select * from user where userAccount=#{userAccount}")
    List<User> selectByuserAccount(String userAccount);
//
//    /**
//     * 插入用户数据
//     *
//     * @param user
//     */
//    int insert(User user);
}
