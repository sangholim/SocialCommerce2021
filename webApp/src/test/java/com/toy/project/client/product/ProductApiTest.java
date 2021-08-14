package com.toy.project.client.product;

import com.toy.project.client.BaseAPITest;
import com.toy.project.domain.*;
import com.toy.project.service.dto.*;
import com.toy.project.web.rest.vm.ProductAddImageVM;
import com.toy.project.web.rest.vm.ProductOptionVM;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiTest {

    // P.1.1 [A] 상품 등록
    @Test
    public void createProductTest() throws Exception {
        String url = "/products";
        String sampleImagePath = "C:\\Users\\hoya\\Desktop\\sample.jpg";
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        String instant = Instant.now().toString();
        // 상품 이름
        multiValueMap.set("name", "zdzd");
        // 상품 종류
        multiValueMap.set("type", "CLASS");

        // 클래스 카테고리 ART-DRAWING, CRAFT-JASU 등록
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryManageId(1l);
        ProductCategoryApiTest.createProductCategoriesParam(multiValueMap, 0, productCategory);
        productCategory = new ProductCategory();
        productCategory.setProductCategoryManageId(2l);
        ProductCategoryApiTest.createProductCategoriesParam(multiValueMap, 1, productCategory);
        // 상품 이름
        multiValueMap.set("name", "zdzd");
        // 상품 종류
        multiValueMap.set("type", "CLASS");
        // 상품 상태
        multiValueMap.set("status", "SELL");
        // 상품 코드
        multiValueMap.set("code", UUID.randomUUID().toString());
        // 클래스인 경우 - 클래스 옵션 사용유무 확인
        multiValueMap.set("useClassOption", "true");
        // 작가, 클래스 상품 구분, 정산율 관련은 productClazzAuthor 매핑
        ProductClazzAuthorDTO productClazzAuthor = new ProductClazzAuthorDTO();
        productClazzAuthor.setClazzId(1l);
        productClazzAuthor.setAuthorId(1l);
        productClazzAuthor.setClassType("ONEDAY");
        productClazzAuthor.setUseCalculation(true);
        productClazzAuthor.setCalculation(50);
        productClazzAuthor.setCalculationUnit("PERCENT");
        productClazzAuthor.setUseCalculationDate(true);
        productClazzAuthor.setCalculationDateFrom(Instant.now());
        productClazzAuthor.setCalculationDateTo(Instant.now());
        productClazzAuthor.setUseDiscount(true);
        productClazzAuthor.setDiscountPrice(30l);
        productClazzAuthor.setDiscountPriceUnit("PERCENT");
        productClazzAuthor.setUseDiscountDate(true);
        productClazzAuthor.setDiscountDateFrom(Instant.now());
        productClazzAuthor.setDiscountDateTo(Instant.now());
        ProductClazzAuthorApiTest.createProductClazzAuthorParam(multiValueMap, productClazzAuthor);
        // 스토어 상품인 경우, 브랜드, 판매자에 따라서 정산율 조정한다.
        ProductStoreDTO productStore = new ProductStoreDTO();
        productStore.setVendorId(1l);
        productStore.setBrandId(1l);
        productStore.setType("DIY");
        productStore.setUseCalculation(true);
        productStore.setUseCalculationDate(true);
        productStore.setCalculation(50);
        productStore.setCalculationUnit("PERCENT");
        productStore.setCalculationDateFrom(Instant.now());
        productStore.setCalculationDateTo(Instant.now());
        ProductStoreApiTest.createProductStoreParam(multiValueMap, productStore);
        // 판매가
        multiValueMap.set("price", "50000");
        // 즉시 할인 사용유무 (PC, AND, IOS)
        multiValueMap.set("useDiscountInstant", "true");
        // 즉시 할인 사용시 (PC)
        ProductDiscountDTO productDiscount = new ProductDiscountDTO();
        productDiscount.setDiscountDevice("PC");
        productDiscount.setDiscountPrice(3000l);
        productDiscount.setDiscountPriceUnit("WON");
        productDiscount.setUseDiscountDate(true);
        productDiscount.setDiscountDateFrom(Instant.now());
        productDiscount.setDiscountDateTo(Instant.now());
        productDiscount.setReducePrice("47000원(3000원 할인)");
        ProductDiscountApiTest.createProductDiscountsParam(multiValueMap, 0, productDiscount);
        // 즉시 할인 사용시 (IOS)
        productDiscount = new ProductDiscountDTO();
        productDiscount.setDiscountDevice("IOS");
        productDiscount.setDiscountPrice(5l);
        productDiscount.setDiscountPriceUnit("PERCENT");
        productDiscount.setUseDiscountDate(true);
        productDiscount.setDiscountDateFrom(Instant.now());
        productDiscount.setDiscountDateTo(Instant.now());
        productDiscount.setReducePrice("47500원(2500원 할인)");
        ProductDiscountApiTest.createProductDiscountsParam(multiValueMap, 1, productDiscount);
        // 할부 isInstallment 여부
        multiValueMap.set("useInstallment", "true");
        // 할부 개월
        multiValueMap.set("installmentMonth", "5");
        // 판매 기간 사용 여부
        multiValueMap.set("useSellDate", "true");
        // 판매 기간
        multiValueMap.set("sellDateFrom", instant.toString());
        multiValueMap.set("sellDateTo", instant.toString());
        // 상품 총 수량
        multiValueMap.set("quantity", "5");
        // 상품 선택 옵션 사용 여부
        multiValueMap.set("useProductOption", "true");
        ProductOptionVM productOption = new ProductOptionVM();
        productOption.setName("베이직 클립지갑 레드");
        productOption.setPackageName("베이직");
        productOption.setDesignName("클립지갑");
        productOption.setColorCode("#123213");
        productOption.setColorName("레드");
        productOption.setUsePackageDescription(true);
        productOption.setPackageDescription("패키지 설명1");
        productOption.setDisplayRecommendPackage(true);

        MultipartFile thumbnail = getMultiPartFile(sampleImagePath);
        productOption.setPackageThumbnail(thumbnail);
        productOption.setPrice("+5000");
        productOption.setQuantity(5l);
        productOption.setStatus("SELL");
        productOption.setOptionCode(UUID.randomUUID().toString());
        ProductOptionApiTest.createProductOptionsParam(multiValueMap, 0, productOption);
        // TODO INPUTOPTION, ADDOPTION
        // 최소 구매량
        multiValueMap.set("minPurchaseAmount", "3");
        // 1회 구매시 최대 횟수
        multiValueMap.set("maxPurchaseAmountPerCount", "3");
        // 1인 최대 구매시
        multiValueMap.set("maxPurchaseAmountPerOne", "3");
        // 상품 메인 이미지 파일
        MultipartFile mainImage = getMultiPartFile(sampleImagePath);
        multiValueMap.set("mainImageFile", mainImage.getResource());
        // 추가 이미지 2개
        MultipartFile addImage1 = getMultiPartFile(sampleImagePath);
        MultipartFile addImage2 = getMultiPartFile(sampleImagePath);
        ProductAddImageVM productAddImage = new ProductAddImageVM();
        productAddImage.setImageFile(addImage1);
        ProductAddImageApiTest.createProductAddImagesParam(multiValueMap, 0, productAddImage);
        productAddImage = new ProductAddImageVM();
        productAddImage.setImageFile(addImage2);
        ProductAddImageApiTest.createProductAddImagesParam(multiValueMap, 1, productAddImage);

        // 상품 메인 비디오 파일
        MultipartFile mainVideo = getMultiPartFile(sampleImagePath);
        multiValueMap.set("mainVideoFile", mainVideo.getResource());
        // 상품 설명 파일
        MultipartFile productDescription = getMultiPartFile(sampleImagePath);
        multiValueMap.set("descriptionFile", productDescription.getResource());
        // 패키지 설명 파일
        PackageDescriptionDTO packageDescription = new PackageDescriptionDTO();
        packageDescription.setContent("패키지 설명 본문 1");
        packageDescription.setSubject("패키지 설명 제목 1");
        // 상세 이미지 1-1, 1-2
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(getMultiPartFile(sampleImagePath));
        fileList.add(getMultiPartFile(sampleImagePath));
        packageDescription.setImageFileList(fileList);
        // 패키지 설명 이미지들
        PackageDescriptionAPITest.createPackageDescriptionParam(multiValueMap, 0, packageDescription);
        packageDescription = new PackageDescriptionDTO();
        packageDescription.setContent("패키지 설명 본문 2");
        packageDescription.setSubject("패키지 설명 제목 2");
        fileList = new ArrayList<>();
        // 상세 이미지 2-1, 2-2
        fileList.add(getMultiPartFile(sampleImagePath));
        fileList.add(getMultiPartFile(sampleImagePath));
        packageDescription.setImageFileList(fileList);
        PackageDescriptionAPITest.createPackageDescriptionParam(multiValueMap, 1, packageDescription);

        // 상품 고시 사용 여부
        multiValueMap.set("useProductAnnounce", "true");
        ProductAnnounceDTO productAnnounce = new ProductAnnounceDTO();
        productAnnounce.setName("상품 고시 제목");
        productAnnounce.setContent("상품 고시 본문");
        // 상세 참조 여부
        productAnnounce.setUseDetail(true);
        ProductAnnounceApiTest.createProductAnnouncesParam(multiValueMap, 0, productAnnounce);
        // 상품 자주 묻는 질문 사용 여부
        multiValueMap.set("useProductFaq", true);
        ProductFaqDTO productFaq = new ProductFaqDTO();
        productFaq.setSequence(0);
        productFaq.setQuestion("상품 자주 묻는 질문");
        productFaq.setAnswer("상품 자주 묻는 답변");
        productFaq.setAnswerDate(Instant.now());
        ProductFaqApiTest.createProductFaqsParam(multiValueMap, 0, productFaq);
        // 상품 배송 출고 유형
        multiValueMap.set("shippingReleaseType", "TODAY");
        // 상품 배송 출고 유형 오늘, NORMAL 일때
        multiValueMap.set("shippingStandardStartTime", instant.toString());
        // 상품 배송 출고 유형 기타일때
        // multiValueMap.set("etcShippingContent", "기타 배송 안내");
        // 상품별 배송비 유형
        multiValueMap.set("separateShippingPriceType", "PAY");
        // 묶음 배송비 유형
        multiValueMap.set("bundleShippingType", "BUNDLE");
        // 기본 배송료
        multiValueMap.set("defaultShippingPrice", "5000");
        // 무료 배송 조건 금액
        multiValueMap.set("freeShippingPrice", "5000");
        // 제주 추가 배송 금액
        multiValueMap.set("jejuShippingPrice", "5000");
        // 제주외 도서 산간 추가 배송 금액
        multiValueMap.set("difficultShippingPrice", "5000");
        // 반품 배송비 (편도)
        multiValueMap.set("refundShippingPrice", "5000");
        // 교환 배송비 (왕복)
        multiValueMap.set("exchangeShippingPrice", "5000");
        // 교환/반품시 안내 템플릿 파일 타입
        multiValueMap.set("exchangeShippingFileType", "EDITOR");
        // 교환/반품시 안내 템플릿 파일 경로
        MultipartFile exchangeShippingFile = getMultiPartFile(sampleImagePath);
        multiValueMap.set("exchangeShippingFile", exchangeShippingFile.getResource());
        // 전시 설정
        multiValueMap.set("useView", "true");
        // 예약 발행 설정
        multiValueMap.set("useViewReservation", "true");
        multiValueMap.set("viewReservationDate", Instant.now().toString());
        // null
        // 상품 공지 노출 여부
        multiValueMap.set("useProductNotice", true);
        multiValueMap.set("productNoticeManageId", 1);
        // 불법 도용 방지 안내 사용여부 ( 상품 템플릿 연결)
        multiValueMap.set("useProductIllegal", true);
        ProductTemplateDTO productTemplateDTO = new ProductTemplateDTO();
        productTemplateDTO.setType("ILLEGAL_USAGE");
        productTemplateDTO.setProductTemplateManageId(1l);
        ProductTemplateApiTest.createProductTemplatesParam(multiValueMap, 0, productTemplateDTO);
        // 추천 상품
        multiValueMap.set("useProductRecommend", true);
        ProductMappingDTO productMapping = new ProductMappingDTO();
        productMapping.setType("RECOMMEND_USE");
        productMapping.setProductMappingManageId(1l);
        ProductMappingApiTest.createProductMappingsParam(multiValueMap, 0, productMapping);
        // 연관 상품
        multiValueMap.set("useProductMapping", true);
        productMapping = new ProductMappingDTO();
        productMapping.setType("RELATION_USE");
        productMapping.setProductMappingManageId(1l);
        ProductMappingApiTest.createProductMappingsParam(multiValueMap, 1, productMapping);
        // 인기, MD 추천, NEW, 하비풀, 초특가 라벨 추가
        // 인기 라벨
        ProductLabelDTO productLabel = new ProductLabelDTO();
        productLabel.setType("POPULAR");
        productLabel.setUseDisplayDate(true);
        productLabel.setDisplayDateFrom(Instant.now());
        productLabel.setDisplayDateTo(Instant.now());
        productLabel.setProductLabelManageId(1l);
        ProductLabelApiTest.createProductLabelsParam(multiValueMap, 0, productLabel);
        // MD 추천 라벨
        productLabel = new ProductLabelDTO();
        productLabel.setType("MD");
        productLabel.setUseDisplayDate(true);
        productLabel.setDisplayDateFrom(Instant.now());
        productLabel.setDisplayDateTo(Instant.now());
        productLabel.setProductLabelManageId(2l);
        ProductLabelApiTest.createProductLabelsParam(multiValueMap, 1, productLabel);
        // NEW 라벨
        productLabel = new ProductLabelDTO();
        productLabel.setType("NEW");
        productLabel.setUseDisplayDate(true);
        productLabel.setDisplayDateFrom(Instant.now());
        productLabel.setDisplayDateTo(Instant.now());
        productLabel.setProductLabelManageId(3l);
        ProductLabelApiTest.createProductLabelsParam(multiValueMap, 2, productLabel);
        // 하비풀용 라벨
        productLabel = new ProductLabelDTO();
        productLabel.setType("HF_ONLY");
        productLabel.setUseDisplayDate(true);
        productLabel.setDisplayDateFrom(Instant.now());
        productLabel.setDisplayDateTo(Instant.now());
        productLabel.setProductLabelManageId(4l);
        ProductLabelApiTest.createProductLabelsParam(multiValueMap, 3, productLabel);
        // 초특가 라벨
        productLabel = new ProductLabelDTO();
        productLabel.setType("SPECIAL");
        productLabel.setUseDisplayDate(true);
        productLabel.setDisplayDateFrom(Instant.now());
        productLabel.setDisplayDateTo(Instant.now());
        productLabel.setProductLabelManageId(5l);
        ProductLabelApiTest.createProductLabelsParam(multiValueMap, 4, productLabel);

        HttpHeaders headers = new HttpHeaders();
        BaseAPITest.setAuthHeaders(headers, "admin", "admin");
        headers.set("Content-type", "multipart/form-data;charset=UTF-8");
        HttpEntity entity = new HttpEntity(multiValueMap, headers);
        BaseAPITest.callClientTest(url, HttpMethod.POST, entity);
    }

    // P.1.4 상품 조회/
    @Test
    public void getProductTest() throws Exception {
        long id = 25;
        String url = "/products/" + id;
        HttpHeaders headers = new HttpHeaders();
        BaseAPITest.setAuthHeaders(headers, "admin", "admin");
        headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity entity = new HttpEntity("", headers);
        BaseAPITest.callClientTest(url, HttpMethod.GET, entity);
    }

    public MultipartFile getMultiPartFile(String filePath) {
        File file = new File(filePath);
        FileItem fileItem = null;
        try (InputStream is = new FileInputStream(file)) {
            fileItem =
                new DiskFileItem(
                    "mainFile",
                    Files.probeContentType(file.toPath()),
                    false,
                    file.getName(),
                    (int) file.length(),
                    file.getParentFile()
                );
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(is, os);
        } catch (Exception e) {}
        return new CommonsMultipartFile(fileItem);
    }
}
