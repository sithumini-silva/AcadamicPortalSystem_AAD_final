package org.example.academic_portal_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionsDTO {
    private Long qid;
    private String name;
    private String content;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private int mcqNumber;
    private int examId;
    private int userId;

    public QuestionsDTO(String name, String content, String option1, String option2, String option3, String option4, String answer, int mcqNumber, int examId, int userId) {
        this.name = name;
        this.content = content;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.mcqNumber = mcqNumber;
        this.examId = examId;
        this.userId = userId;
    }
}
