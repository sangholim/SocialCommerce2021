package com.toy.project.client.product;

import com.toy.project.client.BaseAPITest;
import java.time.Instant;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiTest {

    // P.1.1 [A] 상품 등록
    @Test
    public void createProductTest() throws Exception {
        String url = "/products";
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        String instant = Instant.now().toString();
        multiValueMap.set("name", "zdzd");
        multiValueMap.set("code", "zdzd");
        multiValueMap.set("calculation", "0.05");
        multiValueMap.set("calculationDateFrom", instant);
        multiValueMap.set("calculationDateTo", instant);
        multiValueMap.set("price", 1234);
        multiValueMap.set("allPriceUnit", "KR");
        multiValueMap.set("discount", true);
        multiValueMap.set("discountPrice", 12345);
        multiValueMap.set("discountUnit", "KR");
        multiValueMap.set("discountDateFrom", instant);
        multiValueMap.set("discountDateTo", instant);
        multiValueMap.set("isInstallment", true);
        multiValueMap.set("installmentMonth", 5);
        multiValueMap.set("isSell", true);
        multiValueMap.set("sellDateFrom", instant);
        multiValueMap.set("sellDateTo", instant);
        multiValueMap.set("minPurchaseAmount", 2);
        multiValueMap.set("manPurchaseAmount", 5);
        multiValueMap.set("mainImageFileUrl", "url1");
        multiValueMap.set("addImageFileUrl", "url2");
        multiValueMap.set("mainVideoFileUrl", "url3");
        multiValueMap.set("descriptionFileUrl", "url4");
        multiValueMap.set("shippingType", "type1");
        multiValueMap.set("separateShippingPriceType", "type2");
        multiValueMap.set("defaultShippingPrice", 12345);
        multiValueMap.set("freeShippingPrice", 12345);
        multiValueMap.set("jejuShippingPrice", 12345);
        multiValueMap.set("difficultShippingPrice", 12345);
        multiValueMap.set("refundShippingPrice", 12345);
        multiValueMap.set("exchangeShippingPrice", 12345);
        multiValueMap.set("exchangeShippingFileUrl", 12345);
        multiValueMap.set("isView", true);
        multiValueMap.set("viewReservationDate", instant);
        // child product-category
        multiValueMap.set("productCategories[0].id", 1);
        multiValueMap.set("productCategories[1].id", 2);
        // child product-option
        multiValueMap.set("productOptions[0].id", 1);
        multiValueMap.set("productOptions[1].id", 2);
        // child product-label
        multiValueMap.set("productLabels[0].id", 1);
        multiValueMap.set("productLabels[0].displayDate", true);
        multiValueMap.set("productLabels[0].displayDateFrom", instant);
        multiValueMap.set("productLabels[0].displayDateTo", instant);

        multiValueMap.set("productLabels[1].id", 2);
        multiValueMap.set("productLabels[1].displayDate", true);
        multiValueMap.set("productLabels[1].displayDateFrom", instant);
        multiValueMap.set("productLabels[1].displayDateTo", instant);
        // child product-notice
        multiValueMap.set("productNotices[0].id", 1);
        multiValueMap.set("productNotices[1].id", 2);
        // child product-shipping
        multiValueMap.set("productShippings[0].id", 1);
        multiValueMap.set("productShippings[1].id", 2);
        // child product-template
        multiValueMap.set("productTemplates[0].id", 1);
        multiValueMap.set("productTemplates[1].id", 2);
        // child product-mapping
        multiValueMap.set("productMappings[0].id", 1);
        multiValueMap.set("productMappings[1].id", 2);
        // child product-view
        multiValueMap.set("productViews[0].id", 1);
        multiValueMap.set("productViews[1].id", 2);

        // child store
        multiValueMap.set("stores[0].id", 1);
        multiValueMap.set("stores[0].productUseCalculation", true);
        multiValueMap.set("stores[0].productCalculation", 1);
        multiValueMap.set("stores[0].productCalculationDateFrom", instant);
        multiValueMap.set("stores[0].productCalculationDateTo", instant);

        // child clazz
        multiValueMap.set("clazzs[0].id", 1);
        multiValueMap.set("clazzs[0].productUseCalculation", true);
        multiValueMap.set("clazzs[0].productCalculation", 1);
        multiValueMap.set("clazzs[0].productCalculationDateFrom", instant);
        multiValueMap.set("clazzs[0].productCalculationDateTo", instant);

        HttpHeaders headers = new HttpHeaders();
        BaseAPITest.setAuthHeaders(headers, "admin", "admin");
        headers.set("Content-type", "multipart/form-data;charset=UTF-8");
        HttpEntity entity = new HttpEntity(multiValueMap, headers);
        BaseAPITest.callClientTest(url, HttpMethod.POST, entity);
    }

    //TODO: 상품 등록 수정 API 생성

    // P.1.4 상품 조회/
    @Test
    public void getProductTest() throws Exception {
        long id = 18;
        String url = "/products/" + id;
        HttpHeaders headers = new HttpHeaders();
        BaseAPITest.setAuthHeaders(headers, "admin", "admin");
        headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity entity = new HttpEntity("", headers);
        BaseAPITest.callClientTest(url, HttpMethod.GET, entity);
    }
}
