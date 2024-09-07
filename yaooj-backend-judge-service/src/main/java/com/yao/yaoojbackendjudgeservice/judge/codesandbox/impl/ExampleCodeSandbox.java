package com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl;



import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.enums.JudgeInfoMessageEnum;
import com.yao.yaoojbackendmodel.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * ClassName: ExampleCodeSandbox
 * Package: com.yao.yaoojbackendmy.judge.codesandbox.impl
 * Description:
 * 示例代码沙箱（只为了跑通流程）
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:05
 * @Version 1.0
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeVO executeCode(ExecuteCodeDTO executeCodeDTO) {
        //根据输入得到输出
        List<String> inputList = executeCodeDTO.getInputList();
        String code = executeCodeDTO.getCode();
        String language = executeCodeDTO.getLanguage();

        ExecuteCodeVO executeCodeVO = new ExecuteCodeVO();
        executeCodeVO.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeVO.setOutputList(inputList);
        executeCodeVO.setMessage("测试执行成功！");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setTime(100l);
        judgeInfo.setMemory(100l);
        executeCodeVO.setJudgeInfo(judgeInfo);
        System.out.println("调用示例代码沙箱");
        return executeCodeVO;
    }
}
