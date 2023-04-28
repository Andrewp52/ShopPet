package com.example.shopcl.files.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class FileStorageRepository {
    private final String rootPath;

    public FileStorageRepository(@Value("${storage.root.path:storage}") String rootPath) throws IOException {
        this.rootPath = rootPath;
        checkRoot();
    }

    public boolean isFileExist(String path){
        return Files.exists(resolvePath(path));
    }

    public String saveFile(String path, MultipartFile file) throws IOException {
        Path dir = Path.of(rootPath).resolve(path);
        File f = dir.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        if(!Files.exists(dir)){
            Files.createDirectories(dir);
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f))){
            file.getInputStream().transferTo(bos);
        }
        return Path.of(rootPath).relativize(f.toPath()).toString();
    }

    public InputStream getFileStream(String path) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(Path.of(rootPath, path).toFile()));
    }

    public void deleteFile(String path) throws IOException {
        Files.delete(resolvePath(path));
    }

    public void deleteDirectory(String path) throws IOException {
        FileVisitor<Path> fv = new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
        Path dir = resolvePath(path);
        Files.walkFileTree(dir, fv);
    }

    private void checkRoot() throws IOException {
        if(!Files.exists(Path.of(this.rootPath))){
            Files.createDirectory(Path.of(this.rootPath));
        }
    }

    public List<File> listDirectory(String path) throws IOException{
        try (Stream<Path> fs = Files.list(resolvePath(path))){
            return fs.map(Path::toFile).toList();
        }
    }

    public boolean isDirectory(String path) {
        return Files.isDirectory(resolvePath(path));
    }

    public long getFileSize(String path) throws IOException {
        return Files.size(resolvePath(path));
    }

    public String contentType(String path) throws IOException {
        return Files.probeContentType(resolvePath(path));
    }

    public String createDir(String path) throws IOException {
        Path p = resolvePath(path.replace("../", ""));
        Files.createDirectories(p);
        return Path.of(rootPath).relativize(p).toString();
    }

    public String moveDirOrRenameFile(String path, String newPath) throws IOException {
        Path origin = resolvePath(path);
        Path moveTo = resolvePath(newPath);
        Files.move(origin, moveTo, StandardCopyOption.ATOMIC_MOVE);
        return Path.of(rootPath).relativize(moveTo).toString();
    }

    private Path resolvePath(String path){
        return Path.of(rootPath, path);
    }
}
