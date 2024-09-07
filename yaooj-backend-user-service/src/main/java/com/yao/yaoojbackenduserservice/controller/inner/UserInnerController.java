package com.yao.yaoojbackenduserservice.controller.inner;

import com.yao.yaoojbackendmodel.entity.User;
import com.yao.yaoojbackendserviceclient.service.UserFeignClient;
import com.yao.yaoojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * ClassName: UserInnerController
 * Package: com.yao.yaoojbackenduserservice.controller.inner
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/12 11:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;
    /**
     * 根据 id 获取用户
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId){
        return userService.getById(userId);
    }


    /**
     * 根据 id 获取用户列表
     * @param idList
     * @return
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList){
        return userService.listByIds(idList);
    }
}
