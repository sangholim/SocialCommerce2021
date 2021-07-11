package com.toy.project.service.dto;

import java.time.Instant;

/**
 * Clazz + (Product + Clazz) 관계테이블 컬럼
 */
public class ClazzExtendDTO extends ClazzDTO {

    private Boolean productUseCalculation;

    private String productCalculation;

    private Instant productCalculationDateFrom;

    private Instant productCalculationDateTo;

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