package org.example.academic_portal_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class ResultViewDTO {

    private Integer id;
    private String msg;
    private String totalMark;

    private Integer examId;

    private Integer studentId;
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private User student; // User entity representing the student
//
//    @ManyToOne
//    @JoinColumn(name = "question_id")
//    private Question question; // Question entity representing the question
//
//    private String studentAnswer;


}
