package com.yao.yaoojbackendcommon.common;

import com.yao.yaoojbackendcommon.constant.CommonConstant;
import lombok.Data;

/**
 * ClassName: PageDTO
 * Package: com.yao.yaoojbackendcommon.common
 * Description:
 * 分页请求
 *
 * @Author Joy_瑶
 * @Create 2024/7/24 16:21
 * @Version 1.0
 */
@Data
public class PageDTO {
    /**
     * 当前页号
     */
    private long current=1;
    /**
     * 页面大小
     */
    private long pageSize=10;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder= CommonConstant.SORT_ORDER_ASC;
}
