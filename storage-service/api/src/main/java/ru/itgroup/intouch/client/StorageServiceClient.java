package ru.itgroup.intouch.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itgroup.intouch.FeignConfig.FeignConfig;
import ru.itgroup.intouch.dto.ImageDTO;
import ru.itgroup.intouch.dto.TransformedPhotoDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;

@FeignClient(name = "storage-service", url = "${SN_STORAGE_HOST}:${SN_STORAGE_PORT}",
        path = "/api/v1/storage", configuration = FeignConfig.class)
public interface StorageServiceClient {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ImageDTO feignUploadPhoto(@RequestBody UploadPhotoDto uploadPhotoDto);

    @GetMapping(value = "")
    String feignGetTransformedPhoto(TransformedPhotoDto transformedPhotoDto);
}
