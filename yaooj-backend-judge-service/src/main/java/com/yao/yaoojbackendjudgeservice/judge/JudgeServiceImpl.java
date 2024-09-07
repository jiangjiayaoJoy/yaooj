package com.yao.yaoojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;

import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.yao.yaoojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;
import com.yao.yaoojbackendmodel.dto.JudgeCase;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import com.yao.yaoojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yao.yaoojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: JudgeServiceImpl
 * Package: com.yao.yaoojbackendmy.judge.strategy
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 10:00
 * @Version 1.0
 */
@Component
public class JudgeServiceImpl implements JudgeService{
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1）传入题目的提交id,获取到对应的题目信息，提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if(questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在！");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"题目不存在！");
        }
        // 2）如果题目提交的状态不为等待中，就不用重复执行了
        // 如果成功执行，需要将题目提交的状态改为判题中（也算是一种加乐观锁的思想）！
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中，不能重复判题！");
        }
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态变更失败！");
        }
        // 3）构造参数，调用代码沙箱进行运行代码
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<JudgeCase> judgeCases = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInputCase).collect(Collectors.toList());
        ExecuteCodeDTO executeCodeDTO = ExecuteCodeDTO.builder()
                .language(language)
                .code(code)
                .inputList(inputList)
                .build();
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox=new CodeSandboxProxy(codeSandbox);
        ExecuteCodeVO executeCodeVO = codeSandbox.executeCode(executeCodeDTO);
        // 4）根据沙箱的执行结果，设置题目的判题信息和状态
        // 使用策略模式进行判题信息的比对（因为不同的语言可能判题的策略不相同！）
        List<String> outputList = executeCodeVO.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeVO.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCases);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 5）修改数据库中的判题结果
        questionSubmit = new QuestionSubmit();
        questionSubmit.setId(questionSubmitId);
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        update = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误！");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}
