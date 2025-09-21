package org.example.academic_portal_system.controller;


import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.dto.QuestionsDTO;
import org.example.academic_portal_system.entity.Exam;
import org.example.academic_portal_system.entity.Question;
import org.example.academic_portal_system.entity.User;
import org.example.academic_portal_system.repo.ExamRepo;
import org.example.academic_portal_system.repo.UserRepo;
import org.example.academic_portal_system.service.QuestionService;
import org.example.academic_portal_system.util.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/post")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseUtil addQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            // Convert DTO to Entity
            Question question = new Question();
            question.setName(questionDTO.getName());
            question.setContent(questionDTO.getContent());
            question.setOption1(questionDTO.getOption1());
            question.setOption2(questionDTO.getOption2());
            question.setOption3(questionDTO.getOption3());
            question.setOption4(questionDTO.getOption4());
            question.setAnswer(questionDTO.getAnswer());
            question.setMcqNumber(questionDTO.getMcqNumber());

            // Validate Exam & User
            Exam exam = examRepo.findById(questionDTO.getExamId())
                    .orElseThrow(() -> new IllegalArgumentException("Exam ID Not Found"));
            User user = userRepo.findById(questionDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User ID Not Found"));

            question.setExam(exam);
            question.setUser(user);

            Question savedQuestion = questionService.addQuestion(question);
            return new ResponseUtil(201, "Subject saved successfully", null);
        } catch (Exception e) {
            return new ResponseUtil(500, "Subject saved unsuccessfully", null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestionsByName(@RequestParam String name)  {
        List<Question> questions = questionService.findByName(name);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/mcq/{mcqNumber}")
    public List<Question> getQuestionsByMcq(@PathVariable int mcqNumber) {
        return questionService.findQuestionsByMcqNumber(mcqNumber);
    }

    @GetMapping("get")
    public List<QuestionDTO> getQuestion() {
        List<QuestionDTO> questionDTOS = questionService.getAllQuestions();
        return questionDTOS;
    }


    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }


    @PutMapping("update")
    public ResponseUtil updateQuestion(@RequestBody QuestionsDTO questionDTO) {
        System.out.println("Updating Question: " + questionDTO);

        boolean isUpdated = questionService.updateQuestion(questionDTO);
        if (isUpdated) {
            return new ResponseUtil(200, "Question updated successfully!", null);
        }
        return new ResponseUtil(500, "Error updating question!", null);
    }


    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    //***********************************************
    @GetMapping("/getByExam/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByExam(@PathVariable Long id) {
        try {
            List<Question> questions = questionService.getQuestionsByExams(id);
            List<QuestionDTO> questionDTOs = questions.stream()
                    .map(q -> modelMapper.map(q, QuestionDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(questionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Return 500 if there's an error
        }
    }



}
