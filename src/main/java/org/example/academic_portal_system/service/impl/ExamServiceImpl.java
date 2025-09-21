package org.example.academic_portal_system.service.impl;


import org.example.academic_portal_system.dto.ExamDTO;
import org.example.academic_portal_system.entity.Exam;
import org.example.academic_portal_system.repo.ExamRepo;
import org.example.academic_portal_system.service.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepo examRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean createExam(Exam exam) {
        examRepository.save(exam);
        return true;
    }

    public List<ExamDTO> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                .map(exam -> new ExamDTO(exam.getId(), exam.getDuration(), exam.getDescription(), exam.getStartDate(), exam.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getAllExamIds() {
        return examRepository.findAllIds();
    }

    @Override
    public Exam getExamById(Integer id) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.orElse(null);
    }

    public int getNextExamId() {
        List<Integer> allIds = examRepository.findAllIds();

        if (allIds.isEmpty()) {
            return 1;
        }

        for (int i = 1; i <= allIds.size(); i++) {
            if (!allIds.contains(i)) {
                return i;
            }
        }

        return allIds.size() + 1;
    }

    @Override
    public boolean updateExam(Integer id, Exam exam) {
        if (examRepository.existsById(id)) {
            exam.setId(id);
            examRepository.save(exam);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExam(Integer id) {
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
