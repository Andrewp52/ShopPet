package com.example.shopcl.files.services;

import com.example.shopcl.dto.DtoFactory;
import com.example.shopcl.dto.FileInfoDto;
import com.example.shopcl.files.repositories.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final FileStorageRepository repository;
    private final DtoFactory<FileInfoDto, File> dtoFactory;

    public List<FileInfoDto> listDirectory(String path){
        try {
            return repository.listDirectory(path).stream().map(dtoFactory::getDto).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveFile(String path, MultipartFile file){
        String fullPath = path + "/" + file.getOriginalFilename();
        if(isFileExist(fullPath)){
            return "/" + fullPath;
        }
        try {
            return fixPath(repository.saveFile(path, file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String path){
        if(this.isDirectory(path)){
            deleteDirectory(path);
        } else {
            try {
                repository.deleteFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean isDirectory(String path){
        return repository.isDirectory(path);
    }

    public boolean isFileExist(String path){
        return repository.isFileExist(path);
    }

    public MediaType mediaType(String path){
        try {
            return MediaType.valueOf(repository.contentType(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public InputStreamResource getFile(String path) {
        try {
            return new InputStreamResource(repository.getFileStream(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public long getFileSize(String path) {
        try {
            return repository.getFileSize(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createDir(String path) {
        try {
            return fixPath(repository.createDir(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String moveDirOrRenameFile(String path, String newPath) {
        try {
            return fixPath(repository.moveDirOrRenameFile(path, newPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectory(String path){
        try {
            repository.deleteDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String fixPath(String path){
        return "/" + path.replace("\\", "/");
    }

}
