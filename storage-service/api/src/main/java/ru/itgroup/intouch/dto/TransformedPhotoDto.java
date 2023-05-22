package ru.itgroup.intouch.dto;

import com.cloudinary.Transformation;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransformedPhotoDto {

    private String photoName;
    private Transformation transformation;

    public TransformedPhotoDto(String photoName, Transformation transformation) {
        this.photoName = photoName;
        this.transformation = transformation;
    }
}
