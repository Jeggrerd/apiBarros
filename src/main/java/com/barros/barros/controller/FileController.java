package com.barros.barros.controller;
import com.barros.barros.domain.Archivos;
import com.barros.barros.service.FileStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FileController {


    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

   @Autowired
    private FileStorageService fileStorageService;
    @ApiOperation(value="Subir un archivo", notes="Sube un archivo")
    @PostMapping("/uploadFile/{tipo}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("tipo") String tipo) {
        Archivos archivo = fileStorageService.storeFile(tipo,file);
        String fileDownloadUri =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(tipo.concat("/").concat(archivo.getNombre()))
                        .toUriString();
        return new com.barros.barros.controller.UploadFileResponse(archivo.getNombre(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    @ApiOperation(value="Subir varios archivos", notes="Sube varios archivos")
    @PostMapping("/uploadMultipleFiles/{tipo}")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files")
                                                                MultipartFile[] files, @PathVariable("tipo") String tipo) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,tipo))
                .collect(Collectors.toList());
    }
    @ApiOperation(value="Descargar archivo", notes="Descarga un archivo")
    @GetMapping("/downloadFile/{tipo}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,@PathVariable("tipo")  String tipo,  HttpServletRequest request) {
// Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(tipo,fileName);
// Try to determine file's content type
        String contentType = null;
        try {
            contentType =
            request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
// Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
