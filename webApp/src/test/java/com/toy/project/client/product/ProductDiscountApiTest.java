package com.toy.project.client.product;

import com.toy.project.domain.ProductDiscount_;
import com.toy.project.domain.ProductLabel_;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.dto.ProductLabelDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDiscountApiTest {

    /**
     * post용 상품 라벨 파라미터 생성 (관계 테이블)
     *
     * @param multiValueMap
     */
    public static void createProductDiscountsParam(MultiValueMap multiValueMap, int index, ProductDiscountDTO productDiscount) {
        String prefix = "productDiscountList[" + index + "].";
        multiValueMap.set(prefix + ProductDiscount_.DISCOUNT_DEVICE, productDiscount.getDiscountDevice());
        multiValueMap.set(prefix + ProductDiscount_.DISCOUNT_PRICE, productDiscount.getDiscountPrice());
        multiValueMap.set(prefix + ProductDiscount_.DISCOUNT_PRICE_UNIT, productDiscount.getDiscountPriceUnit());
        multiValueMap.set(prefix + ProductDiscount_.USE_DISCOUNT_DATE, productDiscount.getUseDiscountDate());
        multiValueMap.set(prefix + ProductDiscount_.DISCOUNT_DATE_FROM, productDiscount.getDiscountDateFrom().toString());
        multiValueMap.set(prefix + ProductDiscount_.DISCOUNT_DATE_TO, productDiscount.getDiscountDateTo().toString());
        multiValueMap.set(prefix + ProductDiscount_.REDUCE_PRICE, productDiscount.getReducePrice());
    }
}
