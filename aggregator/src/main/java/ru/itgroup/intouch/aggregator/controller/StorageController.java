package ru.itgroup.intouch.aggregator.controller;

import com.cloudinary.Transformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itgroup.intouch.client.StorageServiceClient;
import ru.itgroup.intouch.dto.TransformedPhotoDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/storage")
public class StorageController {
    private final StorageServiceClient client;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPhoto(@RequestParam("file") MultipartFile multipartFile) {
        try {
            byte[] photo = multipartFile.getBytes();
            return ResponseEntity.ok(client.feignUploadPhoto(new UploadPhotoDto(photo, null)));
        } catch (IOException e) {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("")
    public String transformPhoto(@RequestParam("photoName") String photoName, @RequestParam("transform") Transformation transformation) {
        return client.feignGetTransformedPhoto(new TransformedPhotoDto(photoName, transformation));
    }
}
