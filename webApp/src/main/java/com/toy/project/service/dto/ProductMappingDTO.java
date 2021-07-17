package com.toy.project.service.dto;

import com.toy.project.domain.ProductMapping;
import com.toy.project.domain.ProductShipping;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductMapping} entity.
 */
public class ProductMappingDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    @Lob
    private String content;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductMappingDTO() {}

    public ProductMappingDTO(ProductMapping productMapping) {
        if (productMapping == null) {
            return;
        }
        this.id = productMapping.getId();
        this.name = productMapping.getName();
        this.type = productMapping.getType();
        this.content = productMapping.getContent();
        this.activated = productMapping.getActivated();
        this.createdBy = productMapping.getCreatedBy();
        this.createdDate = productMapping.getCreatedDate();
        this.lastModifiedBy = productMapping.getLastModifiedBy();
        this.lastModifiedDate = productMapping.getLastModifiedDate();
    }

    public static Set<ProductMappingDTO> toSet(Collection<ProductMapping> productMappings) {
        if (CollectionUtils.isEmpty(productMappings)) {
            return null;
        }
        return productMappings.stream().map(ProductMappingDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductMappingDTO> toList(Collection<ProductMapping> productMappings) {
        if (CollectionUtils.isEmpty(productMappings)) {
            return null;
        }
        return productMappings.stream().map(ProductMappingDTO::new).collect(Collectors.toList());
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof ProductMappingDTO)) {
            return false;
        }

        ProductMappingDTO productMappingDTO = (ProductMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMappingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", content='" + getContent() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
