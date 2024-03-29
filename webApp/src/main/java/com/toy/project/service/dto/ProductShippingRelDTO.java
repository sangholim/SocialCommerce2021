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
 * A DTO for the {@link com.toy.project.domain.ProductShippingRel} entity.
 */
public class ProductShippingRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productShippingId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductShippingDTO productShipping;

    public ProductShippingRelDTO() {}

    public ProductShippingRelDTO(Long id, Long productId, Long productShippingId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productShippingId = productShippingId;
        this.activated = activated;
    }

    public static Set<ProductShippingRelDTO> toSet(Long productId, Boolean activated, Collection<ProductShippingDTO> productShippingDTOS) {
        if (CollectionUtils.isEmpty(productShippingDTOS)) {
            return null;
        }
        return productShippingDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productShippingDTO -> new ProductShippingRelDTO(null, productId, productShippingDTO.getId(), activated))
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

    public Long getProductShippingId() {
        return productShippingId;
    }

    public void setProductShippingId(Long productShippingId) {
        this.productShippingId = productShippingId;
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

    public ProductShippingDTO getProductShipping() {
        return productShipping;
    }

    public void setProductShipping(ProductShippingDTO productShipping) {
        this.productShipping = productShipping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductShippingRelDTO)) {
            return false;
        }

        ProductShippingRelDTO productShippingRelDTO = (ProductShippingRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productShippingRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductShippingRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productShipping=" + getProductShipping() +
            "}";
    }
}
