package com.toy.project.client.product;

import com.toy.project.domain.ProductAddOption_;
import com.toy.project.domain.ProductStore_;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductStoreDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductStoreApiTest {

    /**
     * post용 상품 스토어 경우, 브랜드, 판매자, 정산
     * 관련 모델 productStore 매핑
     * @param multiValueMap
     */
    public static void createProductStoreParam(MultiValueMap multiValueMap, ProductStoreDTO productStore) {
        String prefix = "productStore.";
        multiValueMap.set(prefix + "vendorId", productStore.getVendorId());
        multiValueMap.set(prefix + "brandId", productStore.getBrandId());
        multiValueMap.set(prefix + ProductStore_.TYPE, productStore.getType());
        multiValueMap.set(prefix + ProductStore_.USE_CALCULATION, productStore.getUseCalculation());
        multiValueMap.set(prefix + ProductStore_.CALCULATION, productStore.getCalculation());
        multiValueMap.set(prefix + ProductStore_.CALCULATION_UNIT, productStore.getCalculationUnit());

        multiValueMap.set(prefix + ProductStore_.USE_CALCULATION_DATE, productStore.getUseCalculationDate());
        multiValueMap.set(prefix + ProductStore_.CALCULATION_DATE_FROM, productStore.getCalculationDateFrom().toString());
        multiValueMap.set(prefix + ProductStore_.CALCULATION_DATE_TO, productStore.getCalculationDateTo().toString());
    }
}
