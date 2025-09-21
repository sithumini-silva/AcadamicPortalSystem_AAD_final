package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepo extends JpaRepository<Exam, Integer> {
    @Query("SELECT e.id FROM Exam e")
    List<Integer> findAllIds();
}
