package com.toy.project.service.dto;

import com.toy.project.domain.ProductCategory;
import com.toy.project.domain.ProductMapping;
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
 * A DTO for the {@link com.toy.project.domain.ProductCategory} entity.
 */
public class ProductCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private String main;

    private String sub;

    @Lob
    private String description;

    private Integer sortOrder;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductCategoryDTO() {}

    public ProductCategoryDTO(ProductCategory productCategory) {
        if (productCategory == null) {
            return;
        }
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.main = productCategory.getMain();
        this.sub = productCategory.getSub();
        this.description = productCategory.getDescription();
        this.sortOrder = productCategory.getSortOrder();
        this.activated = productCategory.getActivated();
        this.createdBy = productCategory.getCreatedBy();
        this.createdDate = productCategory.getCreatedDate();
        this.lastModifiedBy = productCategory.getLastModifiedBy();
        this.lastModifiedDate = productCategory.getLastModifiedDate();
    }

    public static Set<ProductCategoryDTO> toSet(Collection<ProductCategory> productCategories) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return null;
        }
        return productCategories.stream().map(ProductCategoryDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductCategoryDTO> toList(Collection<ProductCategory> productCategories) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return null;
        }
        return productCategories.stream().map(ProductCategoryDTO::new).collect(Collectors.toList());
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
        if (!(o instanceof ProductCategoryDTO)) {
            return false;
        }

        ProductCategoryDTO productCategoryDTO = (ProductCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", main='" + getMain() + "'" +
            ", sub='" + getSub() + "'" +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
