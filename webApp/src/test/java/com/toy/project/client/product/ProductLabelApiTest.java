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
public class ProductLabelApiTest {

    /**
     * post용 상품 즉시 할인 파라미터 생성
     *
     * @param multiValueMap
     */
    public static void createProductLabelsParam(MultiValueMap multiValueMap, int index, ProductLabelDTO productLabel) {
        String prefix = "productLabelList[" + index + "].";
        // 상품 자주 묻는 질문 순서
        multiValueMap.set(prefix + ProductLabel_.TYPE, productLabel.getType());
        multiValueMap.set(prefix + ProductLabel_.USE_DISPLAY_DATE, productLabel.getUseDisplayDate());
        multiValueMap.set(prefix + ProductLabel_.DISPLAY_DATE_FROM, productLabel.getDisplayDateFrom().toString());
        multiValueMap.set(prefix + ProductLabel_.DISPLAY_DATE_TO, productLabel.getDisplayDateTo().toString());
        multiValueMap.set(prefix + "productLabelManageId", productLabel.getProductLabelManageId());
    }
}
