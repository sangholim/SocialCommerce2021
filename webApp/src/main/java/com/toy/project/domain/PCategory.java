package com.toy.project.domain;

import com.toy.project.domain.enumeration.ProductCategoryType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A PCategory.
 */
@Entity
@Table(name = "p_category")
public class PCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProductCategoryType type;

    @Column(name = "category_main")
    private String categoryMain;

    @Column(name = "category_sub")
    private String categorySub;

    @Column(name = "description")
    private String description;

    @Column(name = "is_use")
    private Integer isUse;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "regdate")
    private Instant regdate;

    @Column(name = "is_use_discount")
    private Integer isUseDiscount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PCategory id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategoryType getType() {
        return this.type;
    }

    public PCategory type(ProductCategoryType type) {
        this.type = type;
        return this;
    }

    public void setType(ProductCategoryType type) {
        this.type = type;
    }

    public String getCategoryMain() {
        return this.categoryMain;
    }

    public PCategory categoryMain(String categoryMain) {
        this.categoryMain = categoryMain;
        return this;
    }

    public void setCategoryMain(String categoryMain) {
        this.categoryMain = categoryMain;
    }

    public String getCategorySub() {
        return this.categorySub;
    }

    public PCategory categorySub(String categorySub) {
        this.categorySub = categorySub;
        return this;
    }

    public void setCategorySub(String categorySub) {
        this.categorySub = categorySub;
    }

    public String getDescription() {
        return this.description;
    }

    public PCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsUse() {
        return this.isUse;
    }

    public PCategory isUse(Integer isUse) {
        this.isUse = isUse;
        return this;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public PCategory sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Instant getRegdate() {
        return this.regdate;
    }

    public PCategory regdate(Instant regdate) {
        this.regdate = regdate;
        return this;
    }

    public void setRegdate(Instant regdate) {
        this.regdate = regdate;
    }

    public Integer getIsUseDiscount() {
        return this.isUseDiscount;
    }

    public PCategory isUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
        return this;
    }

    public void setIsUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PCategory)) {
            return false;
        }
        return id != null && id.equals(((PCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PCategory{" +
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
