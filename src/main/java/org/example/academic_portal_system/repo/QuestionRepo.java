package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByMcqNumber(int mcqNumber);

    List<Question> findByNameContainingIgnoreCase(String keyword);

    List<Question> findByExamId(Long examId);
    List<QuestionDTO> findByExamId(Integer examId);

    @Query("SELECT q.answer FROM Question q WHERE q.qid = :qid")
    List<Integer> findCorrectAnswersByQid(@Param("qid") int qid);

    //****************************************
    @Query("SELECT COUNT(q) FROM Question q WHERE q.exam.id = :examId")
    int countQuestionsByExamId(@Param("examId") int examId);

}
