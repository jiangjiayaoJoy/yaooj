package com.yao.yaoojbackendmodel.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.yao.yaoojbackendmodel.dto.JudgeConfig;
import com.yao.yaoojbackendmodel.entity.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: QuestionVO
 * Package: com.yao.yaoojbackendmy.model.vo
 * Description:
 *
 * @Author Joy_瑶
 * @Create 2024/7/22 17:24
 * @Version 1.0
 */
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

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
     * 创建题目人的信息
     */
    private UserVO userVO;
    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO){
        if(questionVO==null){
            return null;
        }

        Question question = new Question();
        BeanUtil.copyProperties(questionVO,question);

        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSON.toJSONString(tagList));
        }

        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSON.toJSONString(voJudgeConfig));
        }

        return question;
    }

    public static QuestionVO objToVo(Question question){
        if (question == null) {
            return null;
        }

        QuestionVO questionVO = new QuestionVO();
        CopyOptions copyOptions = CopyOptions.create()
                .ignoreError();
        BeanUtil.copyProperties(question,questionVO,copyOptions);

        String tags = question.getTags();
        List<String> tagList = JSONUtil.toList(tags, String.class);
        questionVO.setTags(tagList);

        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr,JudgeConfig.class));
        return questionVO;
    }
}
