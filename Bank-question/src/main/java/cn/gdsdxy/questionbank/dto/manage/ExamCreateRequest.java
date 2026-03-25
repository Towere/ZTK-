package cn.gdsdxy.questionbank.dto.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

/**
 * 试卷创建请求DTO（包含自动组卷规则）
 */
@Data
@Accessors(chain = true)  // 支持链式调用，方便设置属性
@ApiModel(description = "试卷创建及自动组卷请求参数")  // Swagger文档注释
public class ExamCreateRequest {

    // ====================== 试卷基本信息 ======================
    @NotBlank(message = "试卷名称不能为空")
    @ApiModelProperty(value = "试卷名称", required = true, example = "2024级高数期末模拟卷")
    private String source;  // 试卷名称

    @ApiModelProperty(value = "试卷介绍", example = "本试卷涵盖高数上册核心知识点")
    private String description;  // 介绍

    @NotBlank(message = "所属学院不能为空")
    @ApiModelProperty(value = "所属学院", required = true, example = "理学院")
    private String institute;  // 学院

    @NotBlank(message = "所属专业不能为空")
    @ApiModelProperty(value = "所属专业", required = true, example = "数学与应用数学")
    private String major;  // 专业

    @NotBlank(message = "年级不能为空")
    @ApiModelProperty(value = "年级", required = true, example = "2024")
    private String grade;  // 年级

    @NotNull(message = "考试日期不能为空")
    @ApiModelProperty(value = "考试日期", required = true, example = "2024-06-30")
    private String examDate;  // 考试日期

    @NotNull(message = "考试编号不能为空") // 非空验证
    @Positive(message = "考试编号必须为正数") // 正数验证
    @ApiModelProperty(value = "考试编号", required = true, example = "20240630")
    private Integer examCode; // 改为Integer类型


    @NotNull(message = "考试时长不能为空")
    @Positive(message = "考试时长必须为正数")
    @ApiModelProperty(value = "考试时长(分钟)", required = true, example = "90", notes = "最小为10分钟")
    private Integer totalTime;  // 时长(分钟)

    @NotNull(message = "总分不能为空")
    @Positive(message = "总分必须为正数")
    @ApiModelProperty(value = "试卷总分", required = true, example = "100", notes = "最小为10分")
    private Integer totalScore;  // 总分

    @NotBlank(message = "考试类型不能为空")
    @ApiModelProperty(value = "考试类型", required = true, example = "期末模拟")
    private String type;  // 考试类型

    @ApiModelProperty(value = "考生提示", example = "请在规定时间内完成答题，禁止作弊")
    private String tips;  // 考生提示

    // ====================== 自动组卷规则 ======================
    @NotNull(message = "题目总数不能为空")
    @Positive(message = "题目总数必须为正数")
    @ApiModelProperty(value = "组卷题目总数", required = true, example = "20", notes = "5-100题")
    private Integer questionCount;  // 题目总数

    @NotNull(message = "难度分布不能为空")
    @ApiModelProperty(value = "难度分布比例", required = true)
    private DifficultyRatio difficulty;  // 难度分布

    @NotNull(message = "知识点不能为空")
    @ApiModelProperty(value = "需覆盖的知识点ID列表", required = true, example = "[1,2,3]")
    private List<Long> knowledgePoints;  // 知识点ID列表

    /**
     * 嵌套类：难度比例子DTO
     */
    @Data
    @ApiModel(description = "难度分布比例参数")
    public static class DifficultyRatio {
        @PositiveOrZero(message = "容易题占比不能为负数")
        @ApiModelProperty(value = "容易题占比(%)", required = true, example = "30")
        private Integer easy;  // 容易题占比(%)

        @PositiveOrZero(message = "中等题占比不能为负数")
        @ApiModelProperty(value = "中等题占比(%)", required = true, example = "50")
        private Integer medium;  // 中等题占比(%)

        @PositiveOrZero(message = "困难题占比不能为负数")
        @ApiModelProperty(value = "困难题占比(%)", required = true, example = "20")
        private Integer hard;  // 困难题占比(%)
    }
}