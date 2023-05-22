package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.ImageDTO;
import ru.itgroup.intouch.dto.TransformedPhotoDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;
import model.Image;
import ru.itgroup.intouch.service.UploadService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storage")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private final UploadService uploadService;

    @PostMapping("")
    public ImageDTO uploadPhoto(UploadPhotoDto uploadPhotoDto)
    {
        Image image = uploadService.uploadPhoto(uploadPhotoDto);
        return new ImageDTO(image.getPhotoName(), image.getPhotoPath());
    }

    @GetMapping("")
    public String transformPhoto(TransformedPhotoDto transformedPhotoDto) {
        return uploadService.getTransformedPhoto(transformedPhotoDto);
    }
}
