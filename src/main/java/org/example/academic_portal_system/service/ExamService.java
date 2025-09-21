package org.example.academic_portal_system.service;

import org.example.academic_portal_system.dto.ExamDTO;
import org.example.academic_portal_system.entity.Exam;

import java.util.List;

public interface ExamService {
        List<ExamDTO> getAllExams();
        public boolean createExam(Exam exam);
        public Exam getExamById(Integer id);
        public boolean updateExam(Integer id, Exam exam);
        public boolean deleteExam(Integer id);
        public int getNextExamId();
        public List<Integer> getAllExamIds();


}
