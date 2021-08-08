package com.toy.project.client.product;

import com.toy.project.domain.ProductDiscount_;
import com.toy.project.domain.ProductTemplate_;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.dto.ProductTemplateDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTemplateApiTest {

    /**
     * post용 상품 템플레이트 파라미터 생성 (관계 테이블)
     *
     * @param multiValueMap
     */
    public static void createProductTemplatesParam(MultiValueMap multiValueMap, int index, ProductTemplateDTO productTemplate) {
        String prefix = "productTemplateList[" + index + "].";
        // 상품 자주 묻는 질문 순서
        multiValueMap.set(prefix + ProductTemplate_.TYPE, productTemplate.getType());
        multiValueMap.set(prefix + "productTemplateManageId", productTemplate.getProductTemplateManageId());
    }
}
