package com.toy.project.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.toy.project.domain.OptionPackage} entity. This class is used
 * in {@link com.toy.project.web.rest.OptionPackageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /option-packages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OptionPackageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private StringFilter value;

    private BooleanFilter descriptionUsage;

    private BooleanFilter recommendShow;

    private StringFilter thumbnailFileUrl;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productOptionPackageRelId;

    public OptionPackageCriteria() {}

    public OptionPackageCriteria(OptionPackageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.descriptionUsage = other.descriptionUsage == null ? null : other.descriptionUsage.copy();
        this.recommendShow = other.recommendShow == null ? null : other.recommendShow.copy();
        this.thumbnailFileUrl = other.thumbnailFileUrl == null ? null : other.thumbnailFileUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productOptionPackageRelId = other.productOptionPackageRelId == null ? null : other.productOptionPackageRelId.copy();
    }

    @Override
    public OptionPackageCriteria copy() {
        return new OptionPackageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public BooleanFilter getDescriptionUsage() {
        return descriptionUsage;
    }

    public BooleanFilter descriptionUsage() {
        if (descriptionUsage == null) {
            descriptionUsage = new BooleanFilter();
        }
        return descriptionUsage;
    }

    public void setDescriptionUsage(BooleanFilter descriptionUsage) {
        this.descriptionUsage = descriptionUsage;
    }

    public BooleanFilter getRecommendShow() {
        return recommendShow;
    }

    public BooleanFilter recommendShow() {
        if (recommendShow == null) {
            recommendShow = new BooleanFilter();
        }
        return recommendShow;
    }

    public void setRecommendShow(BooleanFilter recommendShow) {
        this.recommendShow = recommendShow;
    }

    public StringFilter getThumbnailFileUrl() {
        return thumbnailFileUrl;
    }

    public StringFilter thumbnailFileUrl() {
        if (thumbnailFileUrl == null) {
            thumbnailFileUrl = new StringFilter();
        }
        return thumbnailFileUrl;
    }

    public void setThumbnailFileUrl(StringFilter thumbnailFileUrl) {
        this.thumbnailFileUrl = thumbnailFileUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getProductOptionPackageRelId() {
        return productOptionPackageRelId;
    }

    public LongFilter productOptionPackageRelId() {
        if (productOptionPackageRelId == null) {
            productOptionPackageRelId = new LongFilter();
        }
        return productOptionPackageRelId;
    }

    public void setProductOptionPackageRelId(LongFilter productOptionPackageRelId) {
        this.productOptionPackageRelId = productOptionPackageRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OptionPackageCriteria that = (OptionPackageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(value, that.value) &&
            Objects.equals(descriptionUsage, that.descriptionUsage) &&
            Objects.equals(recommendShow, that.recommendShow) &&
            Objects.equals(thumbnailFileUrl, that.thumbnailFileUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productOptionPackageRelId, that.productOptionPackageRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            value,
            descriptionUsage,
            recommendShow,
            thumbnailFileUrl,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productOptionPackageRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionPackageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (descriptionUsage != null ? "descriptionUsage=" + descriptionUsage + ", " : "") +
            (recommendShow != null ? "recommendShow=" + recommendShow + ", " : "") +
            (thumbnailFileUrl != null ? "thumbnailFileUrl=" + thumbnailFileUrl + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productOptionPackageRelId != null ? "productOptionPackageRelId=" + productOptionPackageRelId + ", " : "") +
            "}";
    }
}
