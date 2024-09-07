package com.yao.yaoojbackendjudgeservice.judge.codesandbox;


import com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * ClassName: CodeSandboxFactory
 * Package: com.yao.yaoojbackendjudgeservice.judge.codesandbox
 * Description:
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实现）
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:49
 * @Version 1.0
 */
public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
