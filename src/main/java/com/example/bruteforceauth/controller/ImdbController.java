package com.example.bruteforceauth.controller;

import com.example.bruteforceauth.model.ImdbTop250;
import com.example.bruteforceauth.service.ExcelService;
import com.example.bruteforceauth.service.ImdbTop250ParserService;
import com.example.bruteforceauth.service.ExcelEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/imdb")
public class ImdbController {

    @Autowired
    private ImdbTop250ParserService imdbTop250ParserService;
    @Autowired
    private ExcelService excelService;

    @Autowired
    private ExcelEncryptionService encryptionService;

    @PostMapping("/parse")
    public List<ImdbTop250> parseAndSave() throws IOException {
        try {
            return imdbTop250ParserService.fetchAndSaveTop250();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse IMDB", e);
        }
    }

    @GetMapping("/all")
    public List<ImdbTop250> getAll() {
        return imdbTop250ParserService.getAllMovies();
    }
    @GetMapping("/excel")
    public ResponseEntity<ByteArrayResource> downloadExcel() {
        try {
            List<ImdbTop250> movies = imdbTop250ParserService.getAllMovies();
            byte[] excelData = excelService.generateImdbTop250Excel(movies);

            ByteArrayResource resource = new ByteArrayResource(excelData);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename("imdb_top25.xlsx").build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(excelData.length)
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel", e);
        }
    }
    @GetMapping("/excel/protected")
    public ResponseEntity<byte[]> downloadExcelWithPassword(@RequestParam String password) throws Exception {
        List<ImdbTop250> movies = imdbTop250ParserService.getAllMovies();
        byte[] workbook = excelService.generateImdbTop250Excel(movies);//can delete due to prev method
        byte[] encrypted = encryptionService.encryptWorkbook(workbook, password);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=imdb_protected.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(encrypted);
    }
}