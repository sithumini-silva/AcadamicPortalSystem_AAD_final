package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Result;
import org.example.academic_portal_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Result, Integer> {
        // Corrected the method to use User instead of an int for student
        List<Result> findByExamIdAndStudent(Integer examId, User student);
}
