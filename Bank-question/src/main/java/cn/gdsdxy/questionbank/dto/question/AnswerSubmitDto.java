package cn.gdsdxy.questionbank.dto.question;

import lombok.Data;

import java.util.List;

@Data
public class AnswerSubmitDto {
    private Long audioId; // 音频ID
    private List<WrongQuestionDto> wrongQuestions; // 错题列表
}