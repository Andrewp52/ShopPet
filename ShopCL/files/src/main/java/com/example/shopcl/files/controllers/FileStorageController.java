package com.example.shopcl.files.controllers;


import com.example.shopcl.dto.FileInfoDto;
import com.example.shopcl.files.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileStorageController {
    private final FileStorageService service;

    @GetMapping("/**")
    public ResponseEntity<?> getFileOrListDirectory(HttpServletRequest request){
        String path = getFilePath(request);
        if(service.isFileExist(path)){
            if(service.isDirectory(path)){
                return ResponseEntity.ofNullable(getDirectoryList(path));
            }
            return ResponseEntity.ok()
                    .contentType(service.mediaType(path))
                    .body(service.getFile(path));
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/**")
    public String storeFileOrCreateDir(HttpServletRequest request, @RequestParam MultipartFile file){
        String path = getFilePath(request);
        return request.getContextPath() + (file.isEmpty() ? createDir(path) : saveFile(path, file));
    }

    @PutMapping("/**")
    public ResponseEntity<String> moveDirOrRenameFile(HttpServletRequest request, @RequestParam String newPath){
        String path = getFilePath(request);
        if(!service.isFileExist(path)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request.getContextPath() + service.moveDirOrRenameFile(path, newPath));
    }

    @DeleteMapping("/{path}")
    public void deleteFileOrDirectory(@PathVariable(name = "path", required = true) String path){

    }

    private String getFilePath(HttpServletRequest request){
        String[] reqPath = request.getRequestURI().split(request.getContextPath() + "/");
        return reqPath.length > 1 ? reqPath[1] : "";
    }

    private String saveFile(String path, MultipartFile file) {
        return service.saveFile(path, file);
    }

    private String createDir(String path) {
        return service.createDir(path);
    }

    private List<FileInfoDto> getDirectoryList(String path){
        return service.listDirectory(path);
    }
}
