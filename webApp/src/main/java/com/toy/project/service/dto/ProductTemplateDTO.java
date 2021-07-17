package com.toy.project.service.dto;

import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductTemplate;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductTemplate} entity.
 */
public class ProductTemplateDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String contentFileUrl;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductTemplateDTO() {}

    public ProductTemplateDTO(ProductTemplate productTemplate) {
        if (productTemplate == null) {
            return;
        }
        this.id = productTemplate.getId();
        this.name = productTemplate.getName();
        this.type = productTemplate.getType();
        this.contentFileUrl = productTemplate.getContentFileUrl();
        this.activated = productTemplate.getActivated();
        this.createdBy = productTemplate.getCreatedBy();
        this.createdDate = productTemplate.getCreatedDate();
        this.lastModifiedBy = productTemplate.getLastModifiedBy();
        this.lastModifiedDate = productTemplate.getLastModifiedDate();
    }

    public static Set<ProductTemplateDTO> toSet(Collection<ProductTemplate> productTemplates) {
        if (CollectionUtils.isEmpty(productTemplates)) {
            return null;
        }
        return productTemplates.stream().map(ProductTemplateDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductTemplateDTO> toList(Collection<ProductTemplate> productTemplates) {
        if (CollectionUtils.isEmpty(productTemplates)) {
            return null;
        }
        return productTemplates.stream().map(ProductTemplateDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentFileUrl() {
        return contentFileUrl;
    }

    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTemplateDTO)) {
            return false;
        }

        ProductTemplateDTO productTemplateDTO = (ProductTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTemplateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", contentFileUrl='" + getContentFileUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
