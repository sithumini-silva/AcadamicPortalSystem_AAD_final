package org.example.academic_portal_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.academic_portal_system.entity.User;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PdfFileDTO {
    private int id;

    private String fileName;

    private String fileType;

    private String filePath;


    private User user;


}
