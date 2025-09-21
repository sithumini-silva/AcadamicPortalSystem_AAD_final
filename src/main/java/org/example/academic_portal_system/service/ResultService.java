package org.example.academic_portal_system.service;

import org.example.academic_portal_system.dto.ResultDTO;
import org.example.academic_portal_system.dto.ResultViewDTO;

import java.util.List;

public interface ResultService {
    boolean addResult(ResultDTO resultDTO);
    int getNextResultId();
    public List<ResultViewDTO> getAllResults();
    boolean updateResult(int id, ResultDTO resultDTO);
    boolean deleteResult(int id);

    public boolean saveResult(ResultDTO resultDTO);
}
