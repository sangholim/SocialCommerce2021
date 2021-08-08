package com.toy.project.client.product;

import com.toy.project.domain.ProductAddOption_;
import com.toy.project.domain.ProductCategory;
import com.toy.project.domain.ProductCategory_;
import com.toy.project.service.dto.ProductAddOptionDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryApiTest {

    /**
     * post용 상품 카테고리 파라미터 생성
     * @param multiValueMap
     */
    public static void createProductCategoriesParam(MultiValueMap multiValueMap, int index, ProductCategory productCategory) {
        String prefix = "productCategoryList[" + index + "].";
        multiValueMap.set(prefix + "productCategoryManageId", productCategory.getProductCategoryManageId());
    }
}
