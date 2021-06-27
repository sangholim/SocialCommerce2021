package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductOptionColorRel.
 */
@Entity
@Table(name = "product_option_color_rel")
public class ProductOptionColorRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "productOptionRels", "productOptionPackageRels", "productOptionColorRels", "productOptionDesignRels" },
        allowSetters = true
    )
    private ProductOption productOption;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productOptionColorRels" }, allowSetters = true)
    private OptionColor optionColor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductOptionColorRel id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductOptionColorRel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public ProductOptionColorRel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductOptionColorRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductOptionColorRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductOptionColorRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductOptionColorRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductOptionColorRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public ProductOption getProductOption() {
        return this.productOption;
    }

    public ProductOptionColorRel productOption(ProductOption productOption) {
        this.setProductOption(productOption);
        return this;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public OptionColor getOptionColor() {
        return this.optionColor;
    }

    public ProductOptionColorRel optionColor(OptionColor optionColor) {
        this.setOptionColor(optionColor);
        return this;
    }

    public void setOptionColor(OptionColor optionColor) {
        this.optionColor = optionColor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOptionColorRel)) {
            return false;
        }
        return id != null && id.equals(((ProductOptionColorRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOptionColorRel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
