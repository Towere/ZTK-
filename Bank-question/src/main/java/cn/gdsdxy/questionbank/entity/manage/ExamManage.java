package cn.gdsdxy.questionbank.entity.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@ApiModel("试卷管理")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("exam_manage")
public class ExamManage {

    private Integer examCode;

    private String description;

    private String source;
    @TableId
    private Integer paperId;

    private String examDate;

    private Integer totalTime;

    private String grade;

    private String term;

    private String major;

    private String institute;

    private Integer totalScore;

    private String type;

    private String tips;

    @TableField(exist = false)  // 非数据库字段，用于组卷时临时存储
    private Integer questionCount;  // 题目总数

    @TableField(exist = false)
    private DifficultyRatio difficulty;  // 难度分布

    @TableField(exist = false)
    private List<Long> knowledgePoints;  // 知识点ID列表

    @Data
    public static class DifficultyRatio {
        private Integer easy;
        private Integer medium;
        private Integer hard;
    }
}
