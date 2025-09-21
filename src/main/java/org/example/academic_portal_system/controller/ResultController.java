package org.example.academic_portal_system.controller;

import org.example.academic_portal_system.dto.ResultDTO;
import org.example.academic_portal_system.dto.ResultViewDTO;
import org.example.academic_portal_system.service.ResultService;
import org.example.academic_portal_system.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    @Autowired
    private ResultService resultService;


    @PostMapping("save")
    public ResponseUtil saveResult(@RequestBody ResultDTO resultDTO) {
        try {
            // Print received data for debugging
            System.out.println("Received data: " + resultDTO);

            // Ensure correct field mapping
            if (resultDTO.getMsg() == null || resultDTO.getTotalMark() == null) {
                return new ResponseUtil(400, "Invalid result data. Please submit answers first.", null);
            }

            boolean res = resultService.saveResult(resultDTO);
            if (res) {
                return new ResponseUtil(201, "Result saved successfully", null);
            } else {
                return new ResponseUtil(400, "Error: Could not save result.", null);
            }
        } catch (Exception e) {
            return new ResponseUtil(500, "Error saving Result: " + e.getMessage(), null);
        }
    }


    @GetMapping("next-id")
    public int getNextResultId() {
        return resultService.getNextResultId();
    }

    @GetMapping("get")
    public List<ResultViewDTO> getResult() {
        List<ResultViewDTO> results = resultService.getAllResults();
        return results;
    }
//    @PutMapping("update/{id}")
//    public List<ResultDTO> updateResult(@PathVariable int id, @RequestBody ResultDTO resultDTO){
//        return resultService.updateResult(id, resultDTO);
//    }

    @DeleteMapping("delete/{id}")
    public boolean deleteResult(@PathVariable int id){
        return resultService.deleteResult(id);
    }
}