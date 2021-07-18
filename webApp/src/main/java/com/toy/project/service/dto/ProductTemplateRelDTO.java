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
 * A DTO for the {@link com.toy.project.domain.ProductTemplateRel} entity.
 */
public class ProductTemplateRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long productTemplateId;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private ProductTemplateDTO productTemplate;

    public ProductTemplateRelDTO() {}

    public ProductTemplateRelDTO(Long id, Long productId, Long productTemplateId, Boolean activated) {
        this.id = id;
        this.productId = productId;
        this.productTemplateId = productTemplateId;
        this.activated = activated;
    }

    public static Set<ProductTemplateRelDTO> toSet(Long productId, Boolean activated, Collection<ProductTemplateDTO> productTemplateDTOS) {
        if (CollectionUtils.isEmpty(productTemplateDTOS)) {
            return null;
        }
        return productTemplateDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(productTemplateDTO -> new ProductTemplateRelDTO(null, productId, productTemplateDTO.getId(), activated))
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

    public Long getProductTemplateId() {
        return productTemplateId;
    }

    public void setProductTemplateId(Long productTemplateId) {
        this.productTemplateId = productTemplateId;
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

    public ProductTemplateDTO getProductTemplate() {
        return productTemplate;
    }

    public void setProductTemplate(ProductTemplateDTO productTemplate) {
        this.productTemplate = productTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTemplateRelDTO)) {
            return false;
        }

        ProductTemplateRelDTO productTemplateRelDTO = (ProductTemplateRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTemplateRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTemplateRelDTO{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", productTemplate=" + getProductTemplate() +
            "}";
    }
}
