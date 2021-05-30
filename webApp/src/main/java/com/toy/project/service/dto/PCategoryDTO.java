package com.toy.project.service.dto;

import com.toy.project.domain.enumeration.ProductCategoryType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.toy.project.domain.PCategory} entity.
 */
public class PCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private ProductCategoryType type;

    private String categoryMain;

    private String categorySub;

    private String description;

    private Integer isUse;

    private Integer sortOrder;

    private Instant regdate;

    private Integer isUseDiscount;

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

    public ProductCategoryType getType() {
        return type;
    }

    public void setType(ProductCategoryType type) {
        this.type = type;
    }

    public String getCategoryMain() {
        return categoryMain;
    }

    public void setCategoryMain(String categoryMain) {
        this.categoryMain = categoryMain;
    }

    public String getCategorySub() {
        return categorySub;
    }

    public void setCategorySub(String categorySub) {
        this.categorySub = categorySub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Instant getRegdate() {
        return regdate;
    }

    public void setRegdate(Instant regdate) {
        this.regdate = regdate;
    }

    public Integer getIsUseDiscount() {
        return isUseDiscount;
    }

    public void setIsUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PCategoryDTO)) {
            return false;
        }

        PCategoryDTO pCategoryDTO = (PCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", categoryMain='" + getCategoryMain() + "'" +
            ", categorySub='" + getCategorySub() + "'" +
            ", description='" + getDescription() + "'" +
            ", isUse=" + getIsUse() +
            ", sortOrder=" + getSortOrder() +
            ", regdate='" + getRegdate() + "'" +
            ", isUseDiscount=" + getIsUseDiscount() +
            "}";
    }
}
