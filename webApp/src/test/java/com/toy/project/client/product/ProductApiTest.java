package com.toy.project.client.product;

import com.toy.project.client.BaseAPITest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiTest {

    // P.1.1 [A] 상품 등록
    @Test
    public void createProductTest() {
        String url = "/products";

        String json =
            "" +
            "{\n" +
            "    \"name\": \"ax\",\n" +
            "    \"code\": \"axx\",\n" +
            "    \"calculation\": \"0.05\",\n" +
            "    \"calculationDateFrom\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"calculationDateTo\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"price\": 12345,\n" +
            "    \"allPriceUnit\": \"KR\",\n" +
            "    \"discount\": \"ALL\",\n" +
            "    \"discountPrice\": 12345,\n" +
            "    \"discountUnit\": \"KR\",\n" +
            "    \"discountDateFrom\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"discountDateTo\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"isInstallment\": \"false\",\n" +
            "    \"installmentMonth\": 2,\n" +
            "    \"isSell\": \"true\",\n" +
            "    \"sellDateFrom\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"sellDateTo\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"minPurchaseAmount\": 1,\n" +
            "    \"manPurchaseAmount\": 2,\n" +
            "    \"mainImageFileUrl\": \"a\",\n" +
            "    \"addImageFileUrl\": \"a\",\n" +
            "    \"mainVideoFileUrl\": \"a\",\n" +
            "    \"descriptionFileUrl\": \"a\",\n" +
            "    \"shippingType\": \"a\",\n" +
            "    \"separateShippingPriceType\": \"a\",\n" +
            "    \"defaultShippingPrice\": 1234,\n" +
            "    \"freeShippingPrice\": 1,\n" +
            "    \"jejuShippingPrice\": 12,\n" +
            "    \"difficultShippingPrice\": 123,\n" +
            "    \"refundShippingPrice\": 55,\n" +
            "    \"exchangeShippingPrice\": 44,\n" +
            "    \"exchangeShippingFileUrl\": \"z\",\n" +
            "    \"isView\": \"true\",\n" +
            "    \"viewReservationDate\": \"2001-09-09T01:46:40Z\",\n" +
            "    \"activated\": \"true\",\n" +
            // productCategories 카테고리 등록
            "    \"productCategories\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // productOptions;
            "    \"productOptions\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // productLabels
            "    \"productLabels\": [\n" +
            "            {\n" +
            "                \"id\" : 1,\n" +
            "                \"displayDate\" : \"true\",\n" +
            "                \"displayDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"displayDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }," +
            "            {\n" +
            "                \"id\" : 2,\n" +
            "                \"displayDate\" : \"false\",\n" +
            "                \"displayDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"displayDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }" +
            "]," +
            // productNotices
            "    \"productNotices\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // productShippings
            "    \"productShippings\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // productTemplates
            "    \"productTemplates\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // productMappings
            "    \"productMappings\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // stores
            "    \"stores\": [" +
            "            {\n" +
            "                \"id\" : 1,\n" +
            "                \"productUseCalculation\" : \"false\",\n" +
            "                \"productCalculation\" : \"0.05\",\n" +
            "                \"productCalculationDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"productCalculationDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }" +
            "," +
            "            {\n" +
            "                \"id\" : 2,\n" +
            "                \"productUseCalculation\" : \"false\",\n" +
            "                \"productCalculation\" : \"0.05\",\n" +
            "                \"productCalculationDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"productCalculationDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }" +
            "],\n" +
            // productViews
            "    \"productViews\": [{\"id\" : 1},{\"id\" : 2}],\n" +
            // clazzs
            "    \"clazzs\": [" +
            "            {\n" +
            "                \"id\" : 1,\n" +
            "                \"productUseCalculation\" : \"false\",\n" +
            "                \"productCalculation\" : \"0.05\",\n" +
            "                \"productCalculationDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"productCalculationDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }" +
            "," +
            "            {\n" +
            "                \"id\" : 2,\n" +
            "                \"productUseCalculation\" : \"false\",\n" +
            "                \"productCalculation\" : \"0.05\",\n" +
            "                \"productCalculationDateFrom\" : \"2001-09-09T01:46:40Z\",\n" +
            "                \"productCalculationDateTo\" : \"2001-09-09T01:46:40Z\"\n" +
            "            }" +
            "],\n" +
            "    \"dummy\": \"true\"\n" +
            "}\n";

        HttpHeaders headers = new HttpHeaders();
        BaseAPITest.setAuthHeaders(headers, "admin", "admin");
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity entity = new HttpEntity(json, headers);
        BaseAPITest.callClientTest(url, HttpMethod.POST, entity);
    }
}
