package com.toy.project.service.dto;

import com.toy.project.domain.ProductClazzRel;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

/**
 * Clazz + (Product + Clazz) 관계테이블 컬럼
 */
public class ClazzExtendDTO extends ClazzDTO {

    private Boolean productUseCalculation;

    private String productCalculation;

    private Instant productCalculationDateFrom;

    private Instant productCalculationDateTo;

    public ClazzExtendDTO() {}

    public ClazzExtendDTO(ProductClazzRel productClazzRel) {
        super(productClazzRel.getClazz());
        if (productClazzRel == null) {
            return;
        }
        this.productUseCalculation = productClazzRel.getUseCalculation();
        this.productCalculation = productClazzRel.getCalculation();
        this.productCalculationDateFrom = productClazzRel.getCalculationDateFrom();
        this.productCalculationDateTo = productClazzRel.getCalculationDateTo();
    }

    public static Set<ClazzExtendDTO> toExtendSet(Collection<ProductClazzRel> productClazzRels) {
        if (CollectionUtils.isEmpty(productClazzRels)) {
            return null;
        }
        return productClazzRels.stream().map(ClazzExtendDTO::new).collect(Collectors.toSet());
    }

    public static List<ClazzExtendDTO> toExtendList(Collection<ProductClazzRel> productClazzRels) {
        if (CollectionUtils.isEmpty(productClazzRels)) {
            return null;
        }
        return productClazzRels.stream().map(ClazzExtendDTO::new).collect(Collectors.toList());
    }

    public Boolean getProductUseCalculation() {
        return productUseCalculation;
    }

    public void setProductUseCalculation(Boolean productUseCalculation) {
        this.productUseCalculation = productUseCalculation;
    }

    public String getProductCalculation() {
        return productCalculation;
    }

    public void setProductCalculation(String productCalculation) {
        this.productCalculation = productCalculation;
    }

    public Instant getProductCalculationDateFrom() {
        return productCalculationDateFrom;
    }

    public void setProductCalculationDateFrom(Instant productCalculationDateFrom) {
        this.productCalculationDateFrom = productCalculationDateFrom;
    }

    public Instant getProductCalculationDateTo() {
        return productCalculationDateTo;
    }

    public void setProductCalculationDateTo(Instant productCalculationDateTo) {
        this.productCalculationDateTo = productCalculationDateTo;
    }
}
