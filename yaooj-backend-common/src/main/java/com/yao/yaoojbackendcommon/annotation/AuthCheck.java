package com.yao.yaoojbackendcommon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: AuthCheck
 * Package: com.yao.yaoojbackendcommon.annotation
 * Description:
 * 用户身份的权限校验
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 10:09
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {
    /**
     * 必须有某个角色
     * @return
     */
    String mustRole() default "";
}
