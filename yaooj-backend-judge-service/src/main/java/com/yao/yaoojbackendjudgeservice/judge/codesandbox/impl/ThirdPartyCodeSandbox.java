package com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl;


import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;

/**
 * ClassName: ThirdPartyCodeSandbox
 * Package: com.yao.yaoojbackendmy.judge.codesandbox.impl
 * Description:
 * 第三方代码沙箱示例（调用网上现成的代码沙箱）
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:06
 * @Version 1.0
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeVO executeCode(ExecuteCodeDTO executeCodeDTO) {
        System.out.println("调用第三方代码沙箱");
        return new ExecuteCodeVO();
    }
}
