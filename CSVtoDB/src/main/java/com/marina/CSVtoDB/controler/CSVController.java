package com.marina.CSVtoDB.controler;

import com.marina.CSVtoDB.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CSVService fileService;

    @PostMapping("/uploadTotalDays")
    public ResponseEntity uploadFileTotalDays(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (fileService.hasCSVFormat(file)) {
            try {
                fileService.saveTotalVacationDays(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/uploadUsedDays")
    public ResponseEntity uploadFileUsedDays(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (fileService.hasCSVFormat(file)) {
            try {
                fileService.saveUsedVacationDays(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}