package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductClazzAuthor} entity.
 */
public class ProductClazzAuthorDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String classType;

    private Boolean useCalculation;

    private Integer calculation;

    @Size(max = 50)
    private String calculationUnit;

    private Boolean useCalculationDate;

    private Instant calculationDateFrom;

    private Instant calculationDateTo;

    private Boolean useDiscount;

    private Long discountPrice;

    private String discountPriceUnit;

    private Boolean useDiscountDate;

    private Instant discountDateFrom;

    private Instant discountDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long clazzId;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPriceUnit() {
        return discountPriceUnit;
    }

    public void setDiscountPriceUnit(String discountPriceUnit) {
        this.discountPriceUnit = discountPriceUnit;
    }

    public Instant getDiscountDateFrom() {
        return discountDateFrom;
    }

    public void setDiscountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public Instant getDiscountDateTo() {
        return discountDateTo;
    }

    public void setDiscountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
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

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public Boolean getUseDiscount() {
        return useDiscount;
    }

    public void setUseDiscount(Boolean useDiscount) {
        this.useDiscount = useDiscount;
    }

    public Boolean getUseDiscountDate() {
        return useDiscountDate;
    }

    public void setUseDiscountDate(Boolean useDiscountDate) {
        this.useDiscountDate = useDiscountDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductClazzAuthorDTO)) {
            return false;
        }

        ProductClazzAuthorDTO productClazzAuthorDTO = (ProductClazzAuthorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productClazzAuthorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductClazzAuthorDTO{" +
            "id=" + getId() +
            ", classType='" + getClassType() + "'" +
            ", iusesCalculation='" + getUseCalculation() + "'" +
            ", calculation=" + getCalculation() +
            ", calculationUnit='" + getCalculationUnit() + "'" +
            ", useCalculationDate='" + getUseCalculationDate() + "'" +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", useDiscount='" + getUseDiscount() + "'" +
            ", discountPrice=" + getDiscountPrice() +
            ", discountPriceUnit='" + getDiscountPriceUnit() + "'" +
            ", useDiscountDate='" + getUseDiscountDate() + "'" +
            ", discountDateFrom='" + getDiscountDateFrom() + "'" +
            ", discountDateTo='" + getDiscountDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", clazzId=" + getClazzId() +
            ", authorId=" + getAuthorId() +
            "}";
    }
}
