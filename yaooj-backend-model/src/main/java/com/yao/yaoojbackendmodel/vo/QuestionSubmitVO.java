package com.yao.yaoojbackendmodel.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yao.yaoojbackendmodel.dto.JudgeInfo;
import com.yao.yaoojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: QuestionSubmitVO
 * Package: com.yao.yaoojbackendmy.model.vo
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/8/2 21:10
 * @Version 1.0
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 对应题目信息
     */
    private QuestionVO questionVO;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象：
     * 因为对象的judgeInfo直接是json字符串，而包装类的judgeInfo是JudgeInfo类
     * 所以需要转换
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO){
        if(questionSubmitVO==null){
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtil.copyProperties(questionSubmitVO,questionSubmit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if(judgeInfoObj!=null){
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit){
        if(questionSubmit==null){
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        questionSubmitVO.setId(questionSubmit.getId());
        questionSubmitVO.setLanguage(questionSubmit.getLanguage());
        questionSubmitVO.setCode(questionSubmit.getCode());
        questionSubmitVO.setStatus(questionSubmit.getStatus());
        questionSubmitVO.setQuestionId(questionSubmit.getQuestionId());
        questionSubmitVO.setUserId(questionSubmit.getUserId());
        questionSubmitVO.setCreateTime(questionSubmit.getCreateTime());
        questionSubmitVO.setUpdateTime(questionSubmit.getUpdateTime());
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        if(StrUtil.isNotBlank(judgeInfoStr)){
            questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr,JudgeInfo.class));
        }
        return questionSubmitVO;
    }
}
