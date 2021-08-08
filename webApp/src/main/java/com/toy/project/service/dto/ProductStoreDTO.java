package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductStore} entity.
 */
public class ProductStoreDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String type;

    private Boolean useCalculation;

    private Integer calculation;

    @Size(max = 50)
    private String calculationUnit;

    private Boolean useCalculationDate;

    private Instant calculationDateFrom;

    private Instant calculationDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long vendorId;

    private Long brandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCalculation() {
        return calculation;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public String getCalculationUnit() {
        return calculationUnit;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public Boolean getUseCalculation() {
        return useCalculation;
    }

    public void setUseCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
    }

    public Boolean getUseCalculationDate() {
        return useCalculationDate;
    }

    public void setUseCalculationDate(Boolean useCalculationDate) {
        this.useCalculationDate = useCalculationDate;
    }

    public Instant getCalculationDateFrom() {
        return calculationDateFrom;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return calculationDateTo;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStoreDTO)) {
            return false;
        }

        ProductStoreDTO productStoreDTO = (ProductStoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productStoreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ProductStoreDTO{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", useCalculation=" + useCalculation +
            ", calculation=" + calculation +
            ", calculationUnit='" + calculationUnit + '\'' +
            ", useCalculationDate=" + useCalculationDate +
            ", calculationDateFrom=" + calculationDateFrom +
            ", calculationDateTo=" + calculationDateTo +
            ", activated=" + activated +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", vendorId=" + vendorId +
            ", brandId=" + brandId +
            '}';
    }
}
