package com.toy.project.client.product;

import com.toy.project.domain.ProductAddOption_;
import com.toy.project.domain.ProductClazzAuthor_;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductClazzAuthorDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductClazzAuthorApiTest {

    /**
     * post용 상품 클래스인 경우, 작가, 클래스 정보, 정산
     * 관련 모델 productClazzAuthor 매핑
     * @param multiValueMap
     */
    public static void createProductClazzAuthorParam(MultiValueMap multiValueMap, ProductClazzAuthorDTO productClazzAuthor) {
        String prefix = "productClazzAuthor.";
        multiValueMap.set(prefix + "clazzId", productClazzAuthor.getClazzId());
        multiValueMap.set(prefix + "authorId", productClazzAuthor.getAuthorId());
        multiValueMap.set(prefix + ProductClazzAuthor_.CLASS_TYPE, productClazzAuthor.getClassType());
        multiValueMap.set(prefix + ProductClazzAuthor_.USE_CALCULATION, productClazzAuthor.getUseCalculation());
        multiValueMap.set(prefix + ProductClazzAuthor_.CALCULATION, productClazzAuthor.getCalculation());
        multiValueMap.set(prefix + ProductClazzAuthor_.CALCULATION_UNIT, productClazzAuthor.getCalculationUnit());

        multiValueMap.set(prefix + ProductClazzAuthor_.USE_CALCULATION_DATE, productClazzAuthor.getUseCalculationDate());
        multiValueMap.set(prefix + ProductClazzAuthor_.CALCULATION_DATE_FROM, productClazzAuthor.getCalculationDateFrom().toString());
        multiValueMap.set(prefix + ProductClazzAuthor_.CALCULATION_DATE_TO, productClazzAuthor.getCalculationDateTo().toString());

        multiValueMap.set(prefix + ProductClazzAuthor_.USE_DISCOUNT, productClazzAuthor.getUseDiscount());
        multiValueMap.set(prefix + ProductClazzAuthor_.DISCOUNT_PRICE, productClazzAuthor.getDiscountPrice());
        multiValueMap.set(prefix + ProductClazzAuthor_.DISCOUNT_PRICE_UNIT, productClazzAuthor.getDiscountPriceUnit());
        multiValueMap.set(prefix + ProductClazzAuthor_.USE_DISCOUNT_DATE, productClazzAuthor.getUseDiscountDate());
        multiValueMap.set(prefix + ProductClazzAuthor_.DISCOUNT_DATE_FROM, productClazzAuthor.getDiscountDateFrom().toString());
        multiValueMap.set(prefix + ProductClazzAuthor_.DISCOUNT_DATE_TO, productClazzAuthor.getDiscountDateTo().toString());
    }
}
