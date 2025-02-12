package com.dareuda.givetree.media.infrastructure;

import com.dareuda.givetree.common.errors.errorcode.CommonErrorCode;
import com.dareuda.givetree.common.errors.exception.RestApiException;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Repository
public class S3Storage {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Operations s3Operations;

    public String upload(MultipartFile multipartFile, String fileName) {
        log.info("이미지 업로드 - S3Storage");
        try (InputStream is = multipartFile.getResource().getInputStream()) {
            S3Resource s3Resource = s3Operations.upload(bucket, fileName, is,
                    ObjectMetadata.builder()
                            .contentType(multipartFile.getContentType())
                            .build()
            );
            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
