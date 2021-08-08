package com.toy.project.client.product;

import com.toy.project.client.BaseAPITest;
import com.toy.project.domain.*;
import com.toy.project.service.dto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.Instant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMappingApiTest {

    /**
     * post용 추천/연관 상품 파라미터 생성
     *
     * @param multiValueMap
     */
    public static void createProductMappingsParam(MultiValueMap multiValueMap, int index, ProductMappingDTO productMapping) {
        String prefix = "productMappingList[" + index + "].";
        // 상품 자주 묻는 질문 순서
        multiValueMap.set(prefix + ProductMapping_.TYPE, productMapping.getType());
        multiValueMap.set(prefix + "productId", productMapping.getProductId());
        multiValueMap.set(prefix + "productMappingManageId", productMapping.getProductMappingManageId());
    }
}
