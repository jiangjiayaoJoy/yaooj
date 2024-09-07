package com.yao.yaoojbackendjudgeservice.judge.codesandbox;


import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;

/**
 * ClassName: CodeSandbox
 * Package: com.yao.yaoojbackendmy.judge.codesandbox
 * Description:
 * 代码沙箱接口定义
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 16:54
 * @Version 1.0
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeDTO
     * @return
     */
    ExecuteCodeVO executeCode(ExecuteCodeDTO executeCodeDTO);
}
