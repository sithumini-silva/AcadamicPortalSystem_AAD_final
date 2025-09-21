package org.example.academic_portal_system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDTO {
    private int id;
    private String name;
    private int st_count;
    private String date;
    private String time;

//    private User user;
    private Integer userId;

    public SubjectDTO(int id, String name, int st_count) {
        this.id = id;
        this.name = name;
        this.st_count = st_count;
    }

    public SubjectDTO(int id, String name, int st_count, String date, String time) {
        this.id = id;
        this.name = name;
        this.st_count = st_count;
        this.date = date;
        this.time = time;
    }

    public SubjectDTO(int id, String name, int st_count, String date, String time, Integer userId) {
        this.id = id;
        this.name = name;
        this.st_count = st_count;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }
}
