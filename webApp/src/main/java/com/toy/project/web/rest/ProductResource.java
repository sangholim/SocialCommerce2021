package com.toy.project.web.rest;

import com.toy.project.repository.ProductRepository;
import com.toy.project.security.AuthoritiesConstants;
import com.toy.project.service.ProductExtendService;
import com.toy.project.service.ProductQueryService;
import com.toy.project.service.criteria.ProductCriteria;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.dto.ProductExtendDTO;
import com.toy.project.web.rest.errors.BadRequestAlertException;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.toy.project.domain.Product}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    public static final String ENTITY_NAME = "product";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductExtendService productExtendService;

    private final ProductRepository productRepository;

    private final ProductQueryService productQueryService;

    public ProductResource(
        ProductExtendService productExtendService,
        ProductRepository productRepository,
        ProductQueryService productQueryService
    ) {
        this.productExtendService = productExtendService;
        this.productRepository = productRepository;
        this.productQueryService = productQueryService;
    }

    @ApiOperation(
        value = "P.1.1 [A] 상품 등록",
        notes = "" +
        "{<br />" +
        "&nbsp;\"name\": 상품명,<br />" +
        "&nbsp;\"code\": 상품코드,<br />" +
        "&nbsp;\"calculation\": 정산율(0~1),<br />" +
        "&nbsp;\"calculationDateFrom\": 정산 시작일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"calculationDateTo\": 정산 종료일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"price\": 상품 공시가격,<br />" +
        "&nbsp;\"allPriceUnit\": 가격 단위 (WON,DOLLAR),<br />" +
        "&nbsp;\"discount\": 할인타입(ALL, PC, MOBILE, NONE),<br />" +
        "&nbsp;\"discountPrice\": 할인가격(ex: 12,000 (-1,000) ),<br />" +
        "&nbsp;\"discountUnit\": 할인 가격 단위(WON,DOLLAR),<br />" +
        "&nbsp;\"discountDateFrom\": 할인 시작일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"discountDateTo\": 정산 종료일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"isInstallment\": 할부 여부(true,false),<br />" +
        "&nbsp;\"installmentMonth\": 할부 기간(Month),<br />" +
        "&nbsp;\"isSell\": 판매 여부(true, false),<br />" +
        "&nbsp;\"sellDateFrom\": 판매 시작일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"sellDateTo\": 판매 종료일(YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"minPurchaseAmount\": 최소 구매량,<br />" +
        "&nbsp;\"manPurchaseAmount\": 최대 구매량,<br />" +
        "&nbsp;\"mainImageFile\": 메인 이미지 파일,<br />" +
        "&nbsp;\"addImageFile\": 추가 이미지 파일,<br />" +
        "&nbsp;\"mainVideoFile\": 메인 비디오 파일,<br />" +
        "&nbsp;\"descriptionFile\": 상세설명 파일,<br />" +
        "&nbsp;\"shippingType\": 배송 타입(TODAY, NORMAL, ETC),<br />" +
        "&nbsp;\"separateShippingPriceType\": 상품별 배송비 타입 (PAY, FREE, CONDITION_FREE),<br />" +
        "&nbsp;\"defaultShippingPrice\": 배송료,<br />" +
        "&nbsp;\"freeShippingPrice\": 특정 가격 이상 구매시 무료 배송 (ex: xxx원 이상 구매시 무료 배송),<br />" +
        "&nbsp;\"jejuShippingPrice\": 제주도 지역 배송 가격,<br />" +
        "&nbsp;\"difficultShippingPrice\": 제주도를 제외한 도서산간 배송 가격,<br />" +
        "&nbsp;\"refundShippingPrice\": 반품시 배송 가격,<br />" +
        "&nbsp;\"exchangeShippingPrice\": 교환시 배송 가격,<br />" +
        "&nbsp;\"exchangeShippingFile\": 배송/환불/반품 안내 파일,<br />" +
        "&nbsp;\"isView\": 전시 상태 여부[true,false],<br />" +
        "&nbsp;\"viewReservationDate\": 예약 전시 날짜 (YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;\"productCategories\": 등록된 카테고리들 <br /> " +
        "&nbsp;&nbsp;[{\"id\" : 1},{\"id\" : 2}],<br />" +
        "&nbsp;\"productOptions\": 등록된 상품 옵션들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1},{\"id\" : 2}],<br />" +
        "&nbsp;\"productLabels\": 등록된 상품 라벨들 <br />" +
        "&nbsp;[{<br />" +
        "&nbsp;&nbsp;&nbsp;\"id\" : 1,<br />" +
        "&nbsp;&nbsp;&nbsp;\"displayDate\" : 상품 전시 여부 [true, false],<br />" +
        "&nbsp;&nbsp;&nbsp;\"displayDateFrom\" : 상품 전시 시작일 (YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;&nbsp;&nbsp;\"displayDateTo\" : 상품 전시 종료일 (YYYY-MM-DDThh:mm:ssZ)<br />" +
        "&nbsp;&nbsp;},...],<br />" +
        "&nbsp;\"productNotices\": 등록된 상품 공지들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1},...],<br />" +
        "&nbsp;\"productShippings\": 등록된 상품 배송정보들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1},...],<br />" +
        "&nbsp;\"productTemplates\": 등록된 상품 템플릿들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1},...],<br />" +
        "&nbsp;\"productMappings\": 등록된 연관 상품들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1},...],<br />" +
        "&nbsp;\"stores\": 등록된 스토어 상품 정보들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1,<br />" +
        "&nbsp;&nbsp;&nbsp;\"productUseCalculation\" : 정산율 사용 여부 [true, false],<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculation\" : 정산율 (0~1),<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculationDateFrom\" : 정산 시작일 (YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculationDateTo\" : 정산 종료일 (YYYY-MM-DDThh:mm:ssZ)<br />" +
        "&nbsp;&nbsp;},...],<br />" +
        "&nbsp;\"productViews\": [{\"id\" : 1},...],<br />" +
        "&nbsp;\"clazzs\": 등록된 클래스 상품 정보들 <br />" +
        "&nbsp;&nbsp;[{\"id\" : 1,<br />" +
        "&nbsp;&nbsp;&nbsp;\"productUseCalculation\" : 정산율 사용 여부 [true, false],<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculation\" : 정산율 (0~1),<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculationDateFrom\" : 정산 시작일 (YYYY-MM-DDThh:mm:ssZ),<br />" +
        "&nbsp;&nbsp;&nbsp;\"productCalculationDateTo\" : 정산 종료일 (YYYY-MM-DDThh:mm:ssZ)<br />" +
        "&nbsp;&nbsp;},...]<br />" +
        "}<br />"
    )
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(ProductExtendDTO productExtendDTO) throws URISyntaxException {
        // TODO: parameter 유효성 검사
        if (productExtendDTO == null) {
            throw new RuntimeException("not found product data");
        }
        ProductDTO result = productExtendService.save(productExtendDTO);
        return ResponseEntity
            .created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @ApiOperation(value = "P.1.2 [A] 상품 수정")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductDTO productDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Product : {}, {}", id, productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDTO result = productExtendService.save(productDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString()))
            .body(result);
    }

    @ApiOperation(value = "P.1.2.1 [A] 상품 부분 수정")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @PatchMapping(value = "/products/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductDTO> partialUpdateProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductDTO productDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Product partially : {}, {}", id, productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDTO> result = productExtendService.partialUpdate(productDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString())
        );
    }

    @ApiOperation(value = "P.1.3 상품 리스트")
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(ProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);
        Page<ProductDTO> page = productQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @ApiOperation(value = "P.1.3.1 상품 리스트 카운트")
    @GetMapping("/products/count")
    public ResponseEntity<Long> countProducts(ProductCriteria criteria) {
        log.debug("REST request to count Products by criteria: {}", criteria);
        return ResponseEntity.ok().body(productQueryService.countByCriteria(criteria));
    }

    @ApiOperation(value = "P.1.4 상품 조회")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductExtendDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        return ResponseUtil.wrapOrNotFound(productExtendService.findOne(id));
    }

    @ApiOperation(value = "P.1.5 [A] 상품 삭제")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productExtendService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
