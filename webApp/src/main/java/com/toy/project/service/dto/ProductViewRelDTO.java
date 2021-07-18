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
 * A DTO for the {@link com.toy.project.domain.ProductViewRel} entity.
 */
public class ProductViewRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productViewId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductViewDTO productView;

    public ProductViewRelDTO() {}

    public ProductViewRelDTO(Long id, Long productId, Long productViewId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productViewId = productViewId;
        this.activated = activated;
    }

    public static Set<ProductViewRelDTO> toSet(Long productId, Boolean activated, Collection<ProductViewDTO> productViewDTOS) {
        if (CollectionUtils.isEmpty(productViewDTOS)) {
            return null;
        }
        return productViewDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productViewDTO -> new ProductViewRelDTO(null, productId, productViewDTO.getId(), activated))
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

    public Long getProductViewId() {
        return productViewId;
    }

    public void setProductViewId(Long productViewId) {
        this.productViewId = productViewId;
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

    public ProductViewDTO getProductView() {
        return productView;
    }

    public void setProductView(ProductViewDTO productView) {
        this.productView = productView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductViewRelDTO)) {
            return false;
        }

        ProductViewRelDTO productViewRelDTO = (ProductViewRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productViewRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductViewRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productView=" + getProductView() +
            "}";
    }
}
