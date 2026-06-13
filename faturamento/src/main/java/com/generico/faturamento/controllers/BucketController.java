package com.generico.faturamento.controllers;

import com.generico.faturamento.bucket.BucketFile;
import com.generico.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping(path = "/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @RequestMapping(path = "/post")
    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try(InputStream is = file.getInputStream()) {
            MediaType type = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type, file.getSize());

            bucketService.upload(bucketFile);

            return ResponseEntity.ok("arqiovo enviado com sucesso");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar arquivo " + e.getMessage());
        }

    }

    @GetMapping(path = "/get")
    public ResponseEntity<String> getUrl(@RequestParam("filename") String filename){

        try {
            String url = bucketService.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao obter url do arquivo " + e.getMessage());
        }

    }

}
