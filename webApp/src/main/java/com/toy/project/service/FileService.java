package com.toy.project.service;

import com.toy.project.config.ApplicationProperties;
import com.toy.project.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    public static String BASE_PRODUCT_DIR;

    public FileService(ApplicationProperties applicationProperties) throws IOException {
        BASE_PRODUCT_DIR = applicationProperties.getBaseDir() + File.separator + "product";
        Files.createDirectories(Paths.get(BASE_PRODUCT_DIR));
    }

    // 파일 저장시, 파일경로, 리소스 필요
    public String saveProductFile(String dir, String name, MultipartFile multipartFile) {
        // basedir -> product -> 상품 고유번호(dir) -> 리소스들을 저장한다.

        String productFile = "";
        try {
            String productDir = BASE_PRODUCT_DIR + File.separator + dir;
            Files.createDirectories(Paths.get(productDir));
            productFile = productDir + File.separator + name;
            Path filePath = Paths.get(productFile);
            Files.write(filePath, multipartFile.getBytes());
            // "상품 코드/파일" 경로만 저장
            productFile = dir + File.separator + name;
        } catch (Exception e) {
            log.error("fail to save file", e);
            throw new BadRequestAlertException("fail to save file", "product", "save-fail");
        }
        return productFile;
    }
}
