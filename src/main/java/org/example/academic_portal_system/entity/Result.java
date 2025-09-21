package org.example.academic_portal_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String msg;
    private String totalMark;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
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
