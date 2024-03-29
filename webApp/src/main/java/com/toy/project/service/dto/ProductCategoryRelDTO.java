package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductCategoryRel} entity.
 */
public class ProductCategoryRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productCategoryId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductCategoryDTO productCategory;

    public ProductCategoryRelDTO() {}

    public ProductCategoryRelDTO(Long id, Long productId, Long productCategoryId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productCategoryId = productCategoryId;
        this.activated = activated;
    }

    public static Set<ProductCategoryRelDTO> toSet(Long productId, Boolean activated, Collection<ProductCategoryDTO> productCategoryDTOs) {
        if (CollectionUtils.isEmpty(productCategoryDTOs)) {
            return null;
        }
        return productCategoryDTOs
            .stream()
            .filter(Objects::nonNull)
            .map(productCategoryDTO -> new ProductCategoryRelDTO(null, productId, productCategoryDTO.getId(), activated))
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductCategoryDTO getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryDTO productCategory) {
        this.productCategory = productCategory;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategoryRelDTO)) {
            return false;
        }

        ProductCategoryRelDTO productCategoryRelDTO = (ProductCategoryRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productCategoryRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryRelDTO{" +
            "id=" + getId() +
            ", productCategoryId='" + getProductCategoryId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productCategory=" + getProductCategory() +
            "}";
    }
}
