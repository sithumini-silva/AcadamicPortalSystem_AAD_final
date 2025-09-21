package org.example.academic_portal_system.controller;

import org.example.academic_portal_system.entity.PdfFile;
import org.example.academic_portal_system.service.PdfFileService;
import org.example.academic_portal_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/pdf")
public class PdfFileController {
    @Autowired
    private PdfFileService pdfFileService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN'|| 'Admin')")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file,
                                            @RequestParam("userId") int userId) {
        try {
            pdfFileService.savePdf(file, userId);
            return ResponseEntity.ok("PDF uploaded and saved to server successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllPdfs() {
        List<PdfFile> pdfFiles = pdfFileService.getAllPdfs();
        List<Map<String, Object>> pdfList = pdfFiles.stream().map(pdf -> {
            Map<String, Object> pdfMap = new HashMap<>();
            pdfMap.put("id", pdf.getId());
            pdfMap.put("fileName", pdf.getFileName());
            pdfMap.put("filePath", pdf.getFilePath());
            if (pdf.getUser() != null) {
                pdfMap.put("userId", pdf.getUser().getU_id());
                pdfMap.put("userName", pdf.getUser().getName());
            } else {
                pdfMap.put("userId", null);
            }
            return pdfMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pdfList);
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<InputStreamResource> viewPdf(@PathVariable int id) throws IOException {
        PdfFile pdf = pdfFileService.getPdfById(id);
        if (pdf == null) return ResponseEntity.notFound().build();

        File file = new File(pdf.getFilePath());
        if (!file.exists()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename(pdf.getFileName()).build());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable int id) throws IOException {
        PdfFile pdf = pdfFileService.getPdfById(id);
        if (pdf == null) return ResponseEntity.notFound().build();

        File file = new File(pdf.getFilePath());
        if (!file.exists()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(pdf.getFileName()).build());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Admin')")
    public ResponseEntity<String> updatePdf(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {
            PdfFile existingPdf = pdfFileService.getPdfById(id);
            if (existingPdf == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found");
            }


            boolean updated = pdfFileService.updatePdf(id, file);
            if (updated) {
                return ResponseEntity.ok("PDF updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("PDF update failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Admin')")
    public ResponseEntity<String> deletePdf(@PathVariable int id) {
        try {
            PdfFile existingPdf = pdfFileService.getPdfById(id);
            if (existingPdf == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found");
            }

            boolean deleted = pdfFileService.deletePdf(id);
            if (deleted) {
                return ResponseEntity.ok("PDF deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("PDF deletion failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF deletion failed: " + e.getMessage());
        }
    }


}
