package com.toy.project.client.product;

import com.toy.project.domain.ProductAddOption_;
import com.toy.project.domain.ProductAnnounce_;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductAnnounceDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAddOptionApiTest {

    /**
     * post용 상품 추가 옵션 파라미터 생성
     * @param multiValueMap
     */
    public static void createProductAddOptionsParam(MultiValueMap multiValueMap, int index, ProductAddOptionDTO productAddOption) {
        String prefix = "productAddOptionList[" + index + "].";
        // 상품 추가 입력 옵션 사용 여부
        multiValueMap.set(prefix + ProductAddOption_.NAME, productAddOption.getName());
        multiValueMap.set(prefix + ProductAddOption_.VALUE, productAddOption.getValue());
        multiValueMap.set(prefix + ProductAddOption_.PRICE, productAddOption.getPrice());
        multiValueMap.set(prefix + ProductAddOption_.QUANTITY, productAddOption.getQuantity());
        multiValueMap.set(prefix + ProductAddOption_.STATUS, productAddOption.getStatus());
        multiValueMap.set(prefix + ProductAddOption_.OPTION_CODE, productAddOption.getOptionCode());
    }
}
