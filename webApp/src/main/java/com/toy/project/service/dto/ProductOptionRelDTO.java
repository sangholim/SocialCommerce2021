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
 * A DTO for the {@link com.toy.project.domain.ProductOptionRel} entity.
 */
public class ProductOptionRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productOptionId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductOptionDTO productOption;

    public ProductOptionRelDTO() {}

    public ProductOptionRelDTO(Long id, Long productId, Long productOptionId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.activated = activated;
    }

    public static Set<ProductOptionRelDTO> toSet(Long productId, Boolean activated, Collection<ProductOptionDTO> productOptionDTOs) {
        if (CollectionUtils.isEmpty(productOptionDTOs)) {
            return null;
        }
        return productOptionDTOs
            .stream()
            .filter(Objects::nonNull)
            .map(productOptionDTO -> new ProductOptionRelDTO(null, productId, productOptionDTO.getId(), activated))
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

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
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

    public ProductOptionDTO getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOptionDTO productOption) {
        this.productOption = productOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOptionRelDTO)) {
            return false;
        }

        ProductOptionRelDTO productOptionRelDTO = (ProductOptionRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productOptionRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOptionRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productOption=" + getProductOption() +
            "}";
    }
}
