package com.example.shopcl.files.util;

import com.example.shopcl.dto.DtoFactory;
import com.example.shopcl.dto.FileInfoDto;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileInfoDtoFactory implements DtoFactory<FileInfoDto, File> {
    @Override
    public FileInfoDto getDto(File entity) {
        return entity.isDirectory() ?
                new FileInfoDto(entity.getName(), 0,  true) :
                new FileInfoDto(entity.getName(), entity.length(), false);
    }
}
