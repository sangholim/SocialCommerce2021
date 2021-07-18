package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductLabelRel} entity.
 */
public class ProductLabelRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productLabelId;

    private Boolean isDisplayDate;

    private Instant displayDateFrom;

    private Instant displayDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductLabelDTO productLabel;

    public ProductLabelRelDTO() {}

    public ProductLabelRelDTO(
        Long id,
        Long productId,
        Long productLabelId,
        Boolean isDisplayDate,
        Instant displayDateFrom,
        Instant displayDateTo,
        Boolean activated
    ) {
        this.id = id;
        this.productId = productId;
        this.productLabelId = productLabelId;
        this.isDisplayDate = isDisplayDate;
        this.displayDateFrom = displayDateFrom;
        this.displayDateTo = displayDateTo;
        this.activated = activated;
    }

    public static Set<ProductLabelRelDTO> toSet(
        Long productId,
        Boolean activated,
        Collection<ProductLabelExtendDTO> productLabelExtendDTOS
    ) {
        if (CollectionUtils.isEmpty(productLabelExtendDTOS)) {
            return null;
        }

        return productLabelExtendDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(
                productLabelExtendDTO ->
                    new ProductLabelRelDTO(
                        null,
                        productId,
                        productLabelExtendDTO.getId(),
                        productLabelExtendDTO.getDisplayDate(),
                        productLabelExtendDTO.getDisplayDateFrom(),
                        productLabelExtendDTO.getDisplayDateTo(),
                        activated
                    )
            )
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductLabelId() {
        return productLabelId;
    }

    public void setProductLabelId(Long productLabelId) {
        this.productLabelId = productLabelId;
    }

    public Boolean getIsDisplayDate() {
        return isDisplayDate;
    }

    public void setIsDisplayDate(Boolean isDisplayDate) {
        this.isDisplayDate = isDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return displayDateFrom;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return displayDateTo;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ProductLabelDTO getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(ProductLabelDTO productLabel) {
        this.productLabel = productLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductLabelRelDTO)) {
            return false;
        }

        ProductLabelRelDTO productLabelRelDTO = (ProductLabelRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productLabelRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabelRelDTO{" +
            "id=" + getId() +
            ", isDisplayDate='" + getIsDisplayDate() + "'" +
            ", displayDateFrom='" + getDisplayDateFrom() + "'" +
            ", displayDateTo='" + getDisplayDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productLabel=" + getProductLabel() +
            "}";
    }
}
