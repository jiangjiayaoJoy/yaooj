package com.yao.yaoojbackendmodel.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: ExecuteCodeDTO
 * Package: com.yao.yaoojbackendmy.judge.codesandbox.model
 * Description:
 * 代码沙箱接口的请求参数
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 16:56
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteCodeDTO {
    /**
     * 一组输入用例
     */
    private List<String> inputList;
    /**
     * 用户代码
     */
    private String code;
    /**
     * 编程语言
     */
    private String language;
}
