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
 * A DTO for the {@link com.toy.project.domain.ProductNoticeRel} entity.
 */
public class ProductNoticeRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productNoticeId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductNoticeDTO productNotice;

    public ProductNoticeRelDTO() {}

    public ProductNoticeRelDTO(Long id, Long productId, Long productNoticeId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productNoticeId = productNoticeId;
        this.activated = activated;
    }

    public static Set<ProductNoticeRelDTO> toSet(Long productId, Boolean activated, Collection<ProductNoticeDTO> productNoticeDTOS) {
        if (CollectionUtils.isEmpty(productNoticeDTOS)) {
            return null;
        }
        return productNoticeDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productNoticeDTO -> new ProductNoticeRelDTO(null, productId, productNoticeDTO.getId(), activated))
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

    public Long getProductNoticeId() {
        return productNoticeId;
    }

    public void setProductNoticeId(Long productNoticeId) {
        this.productNoticeId = productNoticeId;
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

    public ProductNoticeDTO getProductNotice() {
        return productNotice;
    }

    public void setProductNotice(ProductNoticeDTO productNotice) {
        this.productNotice = productNotice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductNoticeRelDTO)) {
            return false;
        }

        ProductNoticeRelDTO productNoticeRelDTO = (ProductNoticeRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productNoticeRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNoticeRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productNotice=" + getProductNotice() +
            "}";
    }
}
