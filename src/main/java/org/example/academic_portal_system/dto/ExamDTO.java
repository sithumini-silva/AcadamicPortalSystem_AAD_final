package org.example.academic_portal_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.academic_portal_system.entity.Question;
import org.example.academic_portal_system.entity.Result;


import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamDTO {
    private Integer id;
    private String duration;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    private List<Question> questions;

    private List<Result> answers;



    public ExamDTO(int id, String duration, String description, LocalDateTime startDate, LocalDateTime endDate) {
            this.id = id;
            this.duration = duration;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
    }



}
