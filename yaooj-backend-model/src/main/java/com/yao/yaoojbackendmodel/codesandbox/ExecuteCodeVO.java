package com.yao.yaoojbackendmodel.codesandbox;

import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: ExecuteCodeVO
 * Package: com.yao.yaoojbackendmy.judge.codesandbox.model
 * Description:
 * 代码沙箱接口的返回参数
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 16:56
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteCodeVO {
    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 执行输入用例的结果
     */
    private List<String> outputList;

    /**
     * 判题信息（时间消耗、空间消耗等）
     */
    private JudgeInfo judgeInfo;

    /**
     * 接口信息
     */
    private String message;
}
