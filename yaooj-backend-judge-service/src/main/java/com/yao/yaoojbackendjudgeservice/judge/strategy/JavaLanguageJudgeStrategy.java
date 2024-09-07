package com.yao.yaoojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yao.yaoojbackendmodel.dto.JudgeCase;
import com.yao.yaoojbackendmodel.dto.JudgeConfig;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * ClassName: JavaLanguageJudgeStrategy
 * Package: com.yao.yaoojbackendmy.judge.strategy
 * Description:
 * Java程序的判题策略
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 10:59
 * @Version 1.0
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy{
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        //先判断输出列表的长度是否与输入列表一致
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        String message = judgeInfo.getMessage();
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        //先判断沙箱执行结果的输出数量是否和预期输出数量一致
        if(outputList.size()!= inputList.size()){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //依次判断每一项输出和预期输出是否相等
        for(int i=0;i< judgeCaseList.size();i++){
            JudgeCase judgeCase = judgeCaseList.get(i);
            if(!judgeCase.getOutputCase().equals(outputList.get(i))){
                judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        if(memory!=null&&memory!=0&&memory>needMemoryLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //Java程序本身需要额外执行10秒钟
        long JAVA_PROGRAM_TIME_COST=10000L;
        if((time-JAVA_PROGRAM_TIME_COST)>needTimeLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
