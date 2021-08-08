package com.toy.project.client.product;

import com.toy.project.domain.ProductAddOption_;
import com.toy.project.domain.ProductOption_;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.web.rest.vm.ProductOptionVM;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductOptionApiTest {

    /**
     * post용 상품 옵션 파라미터 생성
     * @param multiValueMap
     */
    public static void createProductOptionsParam(MultiValueMap multiValueMap, int index, ProductOptionVM productOption) {
        String prefix = "productOptionList[" + index + "].";

        multiValueMap.set(prefix + ProductOption_.NAME, productOption.getName());
        multiValueMap.set(prefix + ProductOption_.PACKAGE_NAME, productOption.getPackageName());
        multiValueMap.set(prefix + ProductOption_.DESIGN_NAME, productOption.getDesignName());
        multiValueMap.set(prefix + ProductOption_.COLOR_CODE, productOption.getColorCode());
        multiValueMap.set(prefix + ProductOption_.COLOR_NAME, productOption.getColorName());
        multiValueMap.set(prefix + ProductOption_.USE_PACKAGE_DESCRIPTION, productOption.getUsePackageDescription());
        multiValueMap.set(prefix + ProductOption_.PACKAGE_DESCRIPTION, productOption.getPackageDescription());
        multiValueMap.set(prefix + ProductOption_.DISPLAY_RECOMMEND_PACKAGE, productOption.getDisplayRecommendPackage());
        multiValueMap.set(prefix + "packageThumbnail", productOption.getPackageThumbnail().getResource());
        multiValueMap.set(prefix + ProductOption_.PRICE, productOption.getPrice());
        multiValueMap.set(prefix + ProductOption_.QUANTITY, productOption.getQuantity());
        multiValueMap.set(prefix + ProductOption_.STATUS, productOption.getStatus());
        multiValueMap.set(prefix + ProductOption_.OPTION_CODE, productOption.getOptionCode());
    }
}
