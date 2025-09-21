package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.entity.PdfFile;
import org.example.academic_portal_system.entity.User;
import org.example.academic_portal_system.repo.PdfFileRepo;
import org.example.academic_portal_system.repo.UserRepo;
import org.example.academic_portal_system.service.PdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PdfFileServiceImpl implements PdfFileService {
    @Autowired
    private PdfFileRepo pdfFileRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public void savePdf(MultipartFile file, int userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));


        String uploadDir = System.getProperty("user.home") + File.separator + "greenwood-uploads" + File.separator + "pdf";

        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new Exception("Failed to create directory: " + uploadDir);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + extension;
        String fullPath = uploadDir + File.separator + uniqueFileName;

        file.transferTo(new File(fullPath));

        PdfFile pdf = new PdfFile();
        pdf.setFileName(originalFilename);
        pdf.setFileType(file.getContentType());
        pdf.setFilePath(fullPath); // file system path
        pdf.setUser(user);

        pdfFileRepository.save(pdf);
    }



    @Override
    public List<PdfFile> getAllPdfs() {
        return pdfFileRepository.findAll();
    }


//    @Override
//    public List<PdfFile> getPdfsByUser(int userId) {
//        return pdfFileRepository.findAllByUserId(userId);
//    }

    @Override
    public PdfFile getPdfById(int id) {
        return pdfFileRepository.findById(id).orElse(null);
    }

    public boolean updatePdf(int id, MultipartFile file) {
        PdfFile existingPdf = pdfFileRepository.findById(id).orElse(null);
        if (existingPdf == null) {
            throw new RuntimeException("No PDF found with ID " + id);
        }

        try {
            String fileName = file.getOriginalFilename();
            String filePath = "path/to/save/" + fileName;

            file.transferTo(new File(filePath));

            existingPdf.setFileName(fileName);
            existingPdf.setFilePath(filePath);
            pdfFileRepository.save(existingPdf);

            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to update PDF file: " + e.getMessage());
        }
    }

    public boolean deletePdf(int id) {
        PdfFile pdfFile = pdfFileRepository.findById(id).orElse(null);
        if (pdfFile == null) {
            throw new RuntimeException("No PDF found with ID " + id);
        }

        try {
            File file = new File(pdfFile.getFilePath());
            if (file.exists()) {
                file.delete();
            }

            pdfFileRepository.delete(pdfFile);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete PDF file: " + e.getMessage());
        }
    }

}
