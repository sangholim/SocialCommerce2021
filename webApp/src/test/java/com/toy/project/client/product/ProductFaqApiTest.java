package com.toy.project.client.product;

import com.toy.project.domain.ProductDiscount_;
import com.toy.project.domain.ProductFaq_;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.dto.ProductFaqDTO;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductFaqApiTest {

    /**
     * post용 상품 자주묻는 질문 파라미터 생성
     *
     * @param multiValueMap
     */
    public static void createProductFaqsParam(MultiValueMap multiValueMap, int index, ProductFaqDTO productFaq) {
        String prefix = "productFaqList[" + index + "].";
        // 상품 자주 묻는 질문 순서
        multiValueMap.set(prefix + ProductFaq_.SEQUENCE, productFaq.getSequence());
        multiValueMap.set(prefix + ProductFaq_.QUESTION, productFaq.getQuestion());
        multiValueMap.set(prefix + ProductFaq_.ANSWER, productFaq.getAnswer());
        multiValueMap.set(prefix + ProductFaq_.ANSWER_DATE, productFaq.getAnswerDate().toString());
    }
}
