package com.toy.project.service.dto;

import com.toy.project.domain.ProductStoreRel;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class StoreExtendDTO extends StoreDTO {

    private Boolean productUseCalculation;

    private String productCalculation;

    private Instant productCalculationDateFrom;

    private Instant productCalculationDateTo;

    public StoreExtendDTO() {}

    public StoreExtendDTO(ProductStoreRel productStoreRel) {
        super(productStoreRel.getStore());
        if (productStoreRel == null) {
            return;
        }
        this.productUseCalculation = productStoreRel.getUseCalculation();
        this.productCalculation = productStoreRel.getCalculation();
        this.productCalculationDateFrom = productStoreRel.getCalculationDateFrom();
        this.productCalculationDateTo = productStoreRel.getCalculationDateTo();
    }

    public static Set<StoreExtendDTO> toExtendSet(Collection<ProductStoreRel> productStoreRels) {
        if (CollectionUtils.isEmpty(productStoreRels)) {
            return null;
        }
        return productStoreRels.stream().map(StoreExtendDTO::new).collect(Collectors.toSet());
    }

    public static List<StoreExtendDTO> toExtendList(Collection<ProductStoreRel> productStoreRels) {
        if (CollectionUtils.isEmpty(productStoreRels)) {
            return null;
        }
        return productStoreRels.stream().map(StoreExtendDTO::new).collect(Collectors.toList());
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
