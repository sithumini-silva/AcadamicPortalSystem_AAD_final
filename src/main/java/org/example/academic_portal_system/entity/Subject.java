package org.example.academic_portal_system.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int st_count;
    private String date;
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Subject(int id, String name, int st_count) {
        this.id = id;
        this.name = name;
        this.st_count = st_count;
    }

    public Subject(int id, String name, int st_count, String date, String time) {
        this.id = id;
        this.name = name;
        this.st_count = st_count;
        this.date = date;
        this.time = time;
    }

}
