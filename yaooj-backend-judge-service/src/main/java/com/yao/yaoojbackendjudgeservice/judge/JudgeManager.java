package com.yao.yaoojbackendjudgeservice.judge;

import com.yao.yaoojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yao.yaoojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yao.yaoojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yao.yaoojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * ClassName: JudgeManager
 * Package: com.yao.yaoojbackendmy.judge
 * Description:
 * 判题策略的管理（用于简化调用）:
 * 根据不同的条件来判断使用不同的判题策略
 *
 * @Author Joy_瑶
 * @Create 2024/7/29 10:53
 * @Version 1.0
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy=new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy=new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
