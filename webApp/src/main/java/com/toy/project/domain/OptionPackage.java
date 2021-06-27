package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OptionPackage.
 */
@Entity
@Table(name = "option_package")
public class OptionPackage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private String value;

    @Column(name = "description_usage")
    private Boolean descriptionUsage;

    @Column(name = "recommend_show")
    private Boolean recommendShow;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail_file_url")
    private String thumbnailFileUrl;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "optionPackage")
    @JsonIgnoreProperties(value = { "productOption", "optionPackage" }, allowSetters = true)
    private Set<ProductOptionPackageRel> productOptionPackageRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OptionPackage id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public OptionPackage type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public OptionPackage value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getDescriptionUsage() {
        return this.descriptionUsage;
    }

    public OptionPackage descriptionUsage(Boolean descriptionUsage) {
        this.descriptionUsage = descriptionUsage;
        return this;
    }

    public void setDescriptionUsage(Boolean descriptionUsage) {
        this.descriptionUsage = descriptionUsage;
    }

    public Boolean getRecommendShow() {
        return this.recommendShow;
    }

    public OptionPackage recommendShow(Boolean recommendShow) {
        this.recommendShow = recommendShow;
        return this;
    }

    public void setRecommendShow(Boolean recommendShow) {
        this.recommendShow = recommendShow;
    }

    public String getDescription() {
        return this.description;
    }

    public OptionPackage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailFileUrl() {
        return this.thumbnailFileUrl;
    }

    public OptionPackage thumbnailFileUrl(String thumbnailFileUrl) {
        this.thumbnailFileUrl = thumbnailFileUrl;
        return this;
    }

    public void setThumbnailFileUrl(String thumbnailFileUrl) {
        this.thumbnailFileUrl = thumbnailFileUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public OptionPackage activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public OptionPackage createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public OptionPackage createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public OptionPackage lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public OptionPackage lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductOptionPackageRel> getProductOptionPackageRels() {
        return this.productOptionPackageRels;
    }

    public OptionPackage productOptionPackageRels(Set<ProductOptionPackageRel> productOptionPackageRels) {
        this.setProductOptionPackageRels(productOptionPackageRels);
        return this;
    }

    public void setProductOptionPackageRels(Set<ProductOptionPackageRel> productOptionPackageRels) {
        if (this.productOptionPackageRels != null) {
            this.productOptionPackageRels.forEach(i -> i.setOptionPackage(null));
        }
        if (productOptionPackageRels != null) {
            productOptionPackageRels.forEach(i -> i.setOptionPackage(this));
        }
        this.productOptionPackageRels = productOptionPackageRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionPackage)) {
            return false;
        }
        return id != null && id.equals(((OptionPackage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionPackage{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", descriptionUsage='" + getDescriptionUsage() + "'" +
            ", recommendShow='" + getRecommendShow() + "'" +
            ", description='" + getDescription() + "'" +
            ", thumbnailFileUrl='" + getThumbnailFileUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
