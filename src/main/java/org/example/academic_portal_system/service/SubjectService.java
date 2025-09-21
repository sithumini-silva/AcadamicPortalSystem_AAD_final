package org.example.academic_portal_system.service;

import org.example.academic_portal_system.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {
    public boolean addSubject(SubjectDTO subjectDTO);
    public List<SubjectDTO> getAllSubjects();
//    public List<SubjectDTO> updateSubject(int id,SubjectDTO subjectDTO);
    public boolean updateSubject(int id, SubjectDTO subjectDTO);
    public boolean deleteSubject(int id);
    public SubjectDTO getSubjectById(int id);

    public boolean updateSubject(SubjectDTO subjectDTO) ;
}
