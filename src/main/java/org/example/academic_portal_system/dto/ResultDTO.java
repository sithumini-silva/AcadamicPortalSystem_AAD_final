package org.example.academic_portal_system.dto;

import lombok.*;
import org.example.academic_portal_system.entity.Exam;
import org.example.academic_portal_system.entity.User;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter

public class ResultDTO {

    private Integer id;
    private String msg;
    private String totalMark;

    private Exam exam;

    private User student;
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
