package ru.itgroup.intouch.dto;

import com.cloudinary.Transformation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class UploadPhotoDto {
    private byte[] photo;
    private String transformation;

    public UploadPhotoDto(byte[] photo, String transformation) {
        this.photo = photo;
        this.transformation = transformation;
    }
}
