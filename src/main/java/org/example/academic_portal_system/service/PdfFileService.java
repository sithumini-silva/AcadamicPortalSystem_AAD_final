package org.example.academic_portal_system.service;

import org.example.academic_portal_system.entity.PdfFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PdfFileService {
    void savePdf(MultipartFile file, int userId) throws Exception;

    List<PdfFile> getAllPdfs();

//    List<PdfFile> getPdfsByUser(int userId);

    PdfFile getPdfById(int id);
    boolean updatePdf(int id, MultipartFile file);
    boolean deletePdf(int id);

}
