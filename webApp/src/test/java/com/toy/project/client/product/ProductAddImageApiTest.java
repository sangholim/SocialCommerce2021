package com.toy.project.client.product;

import com.toy.project.web.rest.vm.ProductAddImageVM;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAddImageApiTest {

    /**
     * post용 상품 추가 이미지 파람
     * @param multiValueMap
     */
    public static void createProductAddImagesParam(MultiValueMap multiValueMap, int index, ProductAddImageVM productAddImage) {
        String prefix = "productAddImageList[" + index + "].";
        // 상품 추가 입력 옵션 사용 여부
        multiValueMap.set(prefix + "imageFile", productAddImage.getImageFile().getResource());
    }
}
