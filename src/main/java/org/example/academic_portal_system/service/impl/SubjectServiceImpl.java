package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.dto.SubjectDTO;
import org.example.academic_portal_system.entity.Subject;
import org.example.academic_portal_system.entity.User;
import org.example.academic_portal_system.repo.SubjectRepo;
import org.example.academic_portal_system.repo.UserRepo;
import org.example.academic_portal_system.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    public boolean addSubject(SubjectDTO subjectDTO) {
        if (subjectDTO.getUserId() == null || subjectDTO.getUserId() == 0) {
            throw new RuntimeException("User cannot be null or zero!");
        }

        int userId = subjectDTO.getUserId();


        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("User with ID " + userId + " does not exist!")
        );

        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        subject.setUser(user);

        subjectRepo.save(subject);
        return true;
    }

    public SubjectDTO getSubjectById(int id) {
        Subject subject = subjectRepo.findById(id).orElse(null);  // Use .orElse(null) to avoid NoSuchElementException if not found
        if (subject == null) {
            return null;  // If not found, return null
        }
        return modelMapper.map(subject, SubjectDTO.class);  // Map to SubjectDTO
    }


//    public int getNextSubjectId() {
//        List<Integer> allIds = subjectRepo.findAllIds();
//
//        if (allIds.isEmpty()) {
//            return 1;
//        }
//        for (int i = 1; i <= allIds.size(); i++) {
//            if (!allIds.contains(i)) {
//                return i;
//            }
//        }
//
//        return allIds.size() + 1;
//    }

    public int getNextSubjectId() {
        Integer maxId = subjectRepo.findMaxId(); // Fetch the maximum existing ID

        // If no subjects exist, return 1
        if (maxId == null) {
            return 1;
        }

        // Return the next available ID by incrementing the maximum ID
        return maxId + 1;
    }

//    public int getNextSubjectId() {
//        Integer maxId = subjectRepo.findMaxId();
//        return maxId != null ? maxId + 1 : 1;
//    }

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepo.findAll();
        return subjects.stream().map(subject -> {
            SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
            dto.setUserId(subject.getUser() != null ? subject.getUser().getU_id() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean updateSubject(int id, SubjectDTO subjectDTO) {
        return false;
    }


//    public List<SubjectDTO> updateSubject(int id, SubjectDTO subjectDTO) {
//        Subject subject = subjectRepo.findById(id).get();
//        subject.setName(subjectDTO.getName());
//        subject.setSt_count(subjectDTO.getSt_count());
//        subject.setDate(subjectDTO.getDate());
//        subject.setTime(subjectDTO.getTime());
//        subjectRepo.save(subject);
//        return getAllSubjects();
//    }

//    public boolean updateSubject(int id, SubjectDTO subjectDTO) {
//        Subject subject = subjectRepo.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
//        subject.setName(subjectDTO.getName());
//        subject.setSt_count(subjectDTO.getSt_count());
//        subject.setDate(subjectDTO.getDate());
//        subject.setTime(subjectDTO.getTime());
//        subjectRepo.save(subject); // Save the updated subject to the database
//        return true;  // Return true to indicate that the update was successful
//    }

//    public boolean updateSubject(SubjectDTO subjectDTO) {
//        if (subjectRepo.findById(subjectDTO.getId())==null){
//            throw new RuntimeException("no subject found");
//        }
//
//        subjectRepo.save(modelMapper.map(subjectDTO, Subject.class));
//        return true;  // Return true to indicate that the update was successful
//    }

    public boolean updateSubject(SubjectDTO subjectDTO) {
        Subject existing = subjectRepo.findById(subjectDTO.getId()).orElse(null);
        if (existing == null) {
            throw new RuntimeException("No subject found");
        }

        existing.setName(subjectDTO.getName());
        existing.setSt_count(subjectDTO.getSt_count());
        existing.setDate(subjectDTO.getDate());
        existing.setTime(subjectDTO.getTime());

        if (subjectDTO.getUserId() != null) {
            User user = userRepo.findById(subjectDTO.getUserId()).orElse(null);
            if (user == null) throw new RuntimeException("User not found");
            existing.setUser(user);
        }

        subjectRepo.save(existing);
        return true;
    }


    public boolean deleteSubject(int id) {
        if (subjectRepo.existsById(id)) {
            subjectRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
