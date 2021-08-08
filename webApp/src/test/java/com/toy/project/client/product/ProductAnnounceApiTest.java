package com.toy.project.client.product;

import com.toy.project.domain.ProductAnnounce_;
import com.toy.project.domain.ProductFaq_;
import com.toy.project.service.dto.ProductAnnounceDTO;
import com.toy.project.service.dto.ProductFaqDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductAnnounceApiTest {

    /**
     * post용 상품 고시 파라미터 생성
     * @param multiValueMap
     */
    public static void createProductAnnouncesParam(MultiValueMap multiValueMap, int index, ProductAnnounceDTO productAnnounce) {
        String prefix = "productAnnounceList[" + index + "].";
        multiValueMap.set(prefix + ProductAnnounce_.NAME, productAnnounce.getName());
        multiValueMap.set(prefix + ProductAnnounce_.CONTENT, productAnnounce.getContent());
        multiValueMap.set(prefix + ProductAnnounce_.USE_DETAIL, productAnnounce.getUseDetail());
    }
}
