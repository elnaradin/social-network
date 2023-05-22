package ru.itgroup.intouch.dto;

import com.cloudinary.Transformation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class UploadPhotoDto {
    private MultipartFile multipartFile;
    private Transformation transformation;

    public UploadPhotoDto(MultipartFile multipartFile, Transformation transformation) {
        this.multipartFile = multipartFile;
        this.transformation = transformation;
    }
}
