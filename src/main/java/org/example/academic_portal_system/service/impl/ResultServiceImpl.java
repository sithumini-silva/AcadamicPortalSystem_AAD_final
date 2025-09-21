package org.example.academic_portal_system.service.impl;


import org.example.academic_portal_system.dto.ResultDTO;
import org.example.academic_portal_system.dto.ResultViewDTO;
import org.example.academic_portal_system.entity.Exam;
import org.example.academic_portal_system.entity.Result;
import org.example.academic_portal_system.entity.User;
import org.example.academic_portal_system.repo.ExamRepo;
import org.example.academic_portal_system.repo.ResultRepo;
import org.example.academic_portal_system.repo.UserRepo;
import org.example.academic_portal_system.service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addResult(ResultDTO resultDTO) {
        User student = resultDTO.getStudent();

        if (student.getU_id() == 0) {
            student = userRepo.save(student);
        } else {
            student = userRepo.findById(student.getU_id())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
        }

        Result result = modelMapper.map(resultDTO, Result.class);
        result.setStudent(student);
        resultRepo.save(result);

        return true;
    }

    @Override
    public boolean saveResult(ResultDTO resultDTO) {
        try {
            if (resultDTO.getStudent() == null || resultDTO.getStudent().getU_id() == 0) {
                throw new RuntimeException("Invalid Student: Student ID is missing.");
            }

            if (resultDTO.getExam() == null || resultDTO.getExam().getId() == 0) {
                throw new RuntimeException("Invalid Exam: Exam ID is missing.");
            }

            // Fetch related entities
            User student = userRepo.findById(resultDTO.getStudent().getU_id())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Exam exam = examRepo.findById(resultDTO.getExam().getId())
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            // Create and save Result
            Result result = new Result();
            result.setMsg(resultDTO.getMsg() != null ? resultDTO.getMsg() : "N/A");
            result.setTotalMark(resultDTO.getTotalMark() != null ? resultDTO.getTotalMark() : "0");
            result.setExam(exam);
            result.setStudent(student);

            resultRepo.save(result);
            return true;
        } catch (Exception e) {
            System.out.println("Error in saveResult: " + e.getMessage());
            return false;
        }
    }


    @Override
    public int getNextResultId() {
        List<Integer> allIds = resultRepo.findAllIds();
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

    public List<ResultViewDTO> getAllResults() {
        List<Result> results = resultRepo.findAll();

        // Map Result to ResultDTO
        return results.stream().map(result -> {
            // Map the Result entity to ResultDTO
            ResultViewDTO dto = modelMapper.map(result, ResultViewDTO.class);

            // Manually set the extra fields that are not part of the DTO
            Integer examId = result.getExam() != null ? result.getExam().getId() : null;
            Integer studentId = result.getStudent() != null ? result.getStudent().getU_id() : null;

            // Set additional fields manually (although the DTO class is unchanged, you can set the values in the frontend)
            dto.setExamId(examId);  // Make sure you have a method to set this in the DTO if required
            dto.setStudentId(studentId);  // Likewise for studentId

            // Returning the DTO
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public boolean updateResult(int id, ResultDTO resultDTO) {
        Result result = resultRepo.findById(id).orElse(null);
        if (result != null) {
            result.setMsg(resultDTO.getMsg());
            result.setTotalMark(resultDTO.getTotalMark());
            result.setExam(resultDTO.getExam());
            result.setStudent(resultDTO.getStudent());

            resultRepo.save(result);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteResult(int id) {
        if (resultRepo.existsById(id)) {
            resultRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
