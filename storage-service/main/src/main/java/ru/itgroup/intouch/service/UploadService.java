package ru.itgroup.intouch.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itgroup.intouch.config.CloudinaryConfig;
import ru.itgroup.intouch.dto.TransformedPhotoDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;
import model.Image;
import ru.itgroup.intouch.repository.ImageRepository;

import java.util.UUID;

@Service
public class UploadService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(UploadService.class);
    private final CloudinaryConfig cloudinaryConfig;

    @Autowired
    public UploadService(Cloudinary cloudinary, ImageRepository imageRepository, CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    public Image uploadPhoto(MultipartFile multipartFile, Transformation transformation) {
        try {
            String name = UUID.randomUUID().toString();
            String URL = cloudinary.uploader()
                    .upload(multipartFile.getBytes(), ObjectUtils.asMap(
                            "public_id", name,
                            "transformation", transformation
                    ))
                    .get("url")
                    .toString();
            logger.info("Uploaded in cloud: {}", URL);
            Image image = new Image(name, URL);
            imageRepository.saveAndFlush(image);
            logger.info("Saved in DB: {}", image);
            return image;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    public Image uploadPhoto(UploadPhotoDto uploadPhotoDto) {
        logger.info("Received DTO: {}", uploadPhotoDto);
        if(uploadPhotoDto.getTransformation() == null) {
            return uploadPhoto(uploadPhotoDto.getMultipartFile(), new Transformation().width(720).height(480));
        }
        return uploadPhoto(uploadPhotoDto.getMultipartFile(), uploadPhotoDto.getTransformation());
    }

    public String getTransformedPhoto(TransformedPhotoDto transformedPhotoDto) {
        return cloudinary.url().transformation(transformedPhotoDto.getTransformation()).imageTag(transformedPhotoDto.getPhotoName());
    }


}