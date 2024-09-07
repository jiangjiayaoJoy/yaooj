package com.yao.yaoojbackendjudgeservice.judge.strategy;


import com.yao.yaoojbackendmodel.dto.JudgeCase;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.entity.Question;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * ClassName: JudgeContext
 * Package: com.yao.yaoojbackendmy.judge.strategy
 * Description:
 * 判题上下文（用于定义在判题策略中传递的参数）
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 10:47
 * @Version 1.0
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
