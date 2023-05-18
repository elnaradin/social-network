package ru.itgroup.intouch.aggregator.controller;

import com.cloudinary.Transformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itgroup.intouch.client.StorageServiceClient;
import ru.itgroup.intouch.dto.TransformedPhotoDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/storage")
public class StorageController {
    private final StorageServiceClient client;

    @PostMapping(value = "")
    public ResponseEntity uploadPhoto(@RequestParam("image") MultipartFile multipartFile,
                                     @RequestParam(name = "transform", required = false) Transformation transformation) {
        return ResponseEntity.ok(client.feignUploadPhoto(new UploadPhotoDto(multipartFile, transformation)));
    }

    @GetMapping("")
    public String transformPhoto(@RequestParam("photoName") String photoName, @RequestParam("transform") Transformation transformation) {
        return client.feignGetTransformedPhoto(new TransformedPhotoDto(photoName, transformation));
    }
}
