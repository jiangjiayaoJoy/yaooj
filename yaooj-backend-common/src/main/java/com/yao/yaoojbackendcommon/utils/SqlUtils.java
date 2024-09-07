package com.yao.yaoojbackendcommon.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: SqlUtils
 * Package: com.yao.yaoojbackendmy.utils
 * Description:
 * SQL工具
 *
 * @Author Joy_瑶
 * @Create 2024/7/24 17:15
 * @Version 1.0
 */
public class SqlUtils {
    /**
     * 校验排序字段是否合法（防止SQL注入）
     * @return
     */
    public static boolean validSortField(String sortField){
        if(StringUtils.isBlank(sortField)){
            return false;
        }
        return !StringUtils.containsAny(sortField,"=","(",")"," ","'");
    }
}
