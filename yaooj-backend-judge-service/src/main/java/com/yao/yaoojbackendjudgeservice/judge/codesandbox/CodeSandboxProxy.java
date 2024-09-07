package com.yao.yaoojbackendjudgeservice.judge.codesandbox;

import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: CodeSandboxProxy
 * Package: com.yao.yaoojbackendmy.judge.codesandbox
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 22:13
 * @Version 1.0
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{
    private final CodeSandbox codeSandbox;
    public CodeSandboxProxy(CodeSandbox codeSandbox){
        this.codeSandbox=codeSandbox;
    }
    @Override
    public ExecuteCodeVO executeCode(ExecuteCodeDTO executeCodeDTO) {
        log.info("代码沙箱请求信息："+executeCodeDTO.toString());
        ExecuteCodeVO executeCodeVO = codeSandbox.executeCode(executeCodeDTO);
        log.info("代码沙箱响应信息："+executeCodeVO);
        return executeCodeVO;
    }
}
