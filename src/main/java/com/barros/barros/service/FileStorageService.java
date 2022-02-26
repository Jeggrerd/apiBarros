package com.barros.barros.service;

import com.barros.barros.domain.Archivos;
import com.barros.barros.exception.FileNotFoundException;
import com.barros.barros.exception.FileStorageException;
import com.barros.barros.property.FileStorageProperties;
import com.barros.barros.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final ArchivoRepository archivoRepository;
    private final Path fileStorageLocation;
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, ArchivoRepository archivoRepository, ArchivoRepository archivoRepository1) {
        this.fileStorageLocation =
                Paths.get(fileStorageProperties.getUploadDir())
                        .toAbsolutePath().normalize();
        this.archivoRepository = archivoRepository1;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public Archivos storeFile(String subfolder, MultipartFile file) {

// Normalize file name
        // Normalize file name
        String fileName = StringUtils.cleanPath(subfolder+"/"+file.getOriginalFilename());
        try {
// Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Archivos a1= new Archivos();
            a1.setTipo(subfolder);
            a1.setNombre(fileName);

            return archivoRepository.saveAndFlush(a1);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public Resource loadFileAsResource(String tipo,String fileName) {
        try {
            Path filePath =
                    this.fileStorageLocation.resolve(tipo+"/"+fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

}
