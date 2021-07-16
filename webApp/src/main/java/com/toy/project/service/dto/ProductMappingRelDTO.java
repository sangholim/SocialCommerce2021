package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductMappingRel} entity.
 */
public class ProductMappingRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productMappingId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductMappingDTO productMapping;

    public ProductMappingRelDTO() {}

    public ProductMappingRelDTO(Long id, Long productId, Long productMappingId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productMappingId = productMappingId;
        this.activated = activated;
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

    public Long getProductMappingId() {
        return productMappingId;
    }

    public void setProductMappingId(Long productMappingId) {
        this.productMappingId = productMappingId;
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

    public ProductMappingDTO getProductMapping() {
        return productMapping;
    }

    public void setProductMapping(ProductMappingDTO productMapping) {
        this.productMapping = productMapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductMappingRelDTO)) {
            return false;
        }

        ProductMappingRelDTO productMappingRelDTO = (ProductMappingRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productMappingRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMappingRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productMapping=" + getProductMapping() +
            "}";
    }
}
