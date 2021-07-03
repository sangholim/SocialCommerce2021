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
 * Criteria class for the {@link com.toy.project.domain.ProductNotice} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductNoticeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-notices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductNoticeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter type;

    private StringFilter contentFileUrl;

    private BooleanFilter priorityDisplay;

    private BooleanFilter allProductDisplay;

    private StringFilter target;

    private BooleanFilter enableDisplayDate;

    private InstantFilter displayDateFrom;

    private InstantFilter displayDateTo;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productNoticeRelId;

    public ProductNoticeCriteria() {}

    public ProductNoticeCriteria(ProductNoticeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.contentFileUrl = other.contentFileUrl == null ? null : other.contentFileUrl.copy();
        this.priorityDisplay = other.priorityDisplay == null ? null : other.priorityDisplay.copy();
        this.allProductDisplay = other.allProductDisplay == null ? null : other.allProductDisplay.copy();
        this.target = other.target == null ? null : other.target.copy();
        this.enableDisplayDate = other.enableDisplayDate == null ? null : other.enableDisplayDate.copy();
        this.displayDateFrom = other.displayDateFrom == null ? null : other.displayDateFrom.copy();
        this.displayDateTo = other.displayDateTo == null ? null : other.displayDateTo.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productNoticeRelId = other.productNoticeRelId == null ? null : other.productNoticeRelId.copy();
    }

    @Override
    public ProductNoticeCriteria copy() {
        return new ProductNoticeCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public StringFilter getContentFileUrl() {
        return contentFileUrl;
    }

    public StringFilter contentFileUrl() {
        if (contentFileUrl == null) {
            contentFileUrl = new StringFilter();
        }
        return contentFileUrl;
    }

    public void setContentFileUrl(StringFilter contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }

    public BooleanFilter getPriorityDisplay() {
        return priorityDisplay;
    }

    public BooleanFilter priorityDisplay() {
        if (priorityDisplay == null) {
            priorityDisplay = new BooleanFilter();
        }
        return priorityDisplay;
    }

    public void setPriorityDisplay(BooleanFilter priorityDisplay) {
        this.priorityDisplay = priorityDisplay;
    }

    public BooleanFilter getAllProductDisplay() {
        return allProductDisplay;
    }

    public BooleanFilter allProductDisplay() {
        if (allProductDisplay == null) {
            allProductDisplay = new BooleanFilter();
        }
        return allProductDisplay;
    }

    public void setAllProductDisplay(BooleanFilter allProductDisplay) {
        this.allProductDisplay = allProductDisplay;
    }

    public StringFilter getTarget() {
        return target;
    }

    public StringFilter target() {
        if (target == null) {
            target = new StringFilter();
        }
        return target;
    }

    public void setTarget(StringFilter target) {
        this.target = target;
    }

    public BooleanFilter getEnableDisplayDate() {
        return enableDisplayDate;
    }

    public BooleanFilter enableDisplayDate() {
        if (enableDisplayDate == null) {
            enableDisplayDate = new BooleanFilter();
        }
        return enableDisplayDate;
    }

    public void setEnableDisplayDate(BooleanFilter enableDisplayDate) {
        this.enableDisplayDate = enableDisplayDate;
    }

    public InstantFilter getDisplayDateFrom() {
        return displayDateFrom;
    }

    public InstantFilter displayDateFrom() {
        if (displayDateFrom == null) {
            displayDateFrom = new InstantFilter();
        }
        return displayDateFrom;
    }

    public void setDisplayDateFrom(InstantFilter displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public InstantFilter getDisplayDateTo() {
        return displayDateTo;
    }

    public InstantFilter displayDateTo() {
        if (displayDateTo == null) {
            displayDateTo = new InstantFilter();
        }
        return displayDateTo;
    }

    public void setDisplayDateTo(InstantFilter displayDateTo) {
        this.displayDateTo = displayDateTo;
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

    public LongFilter getProductNoticeRelId() {
        return productNoticeRelId;
    }

    public LongFilter productNoticeRelId() {
        if (productNoticeRelId == null) {
            productNoticeRelId = new LongFilter();
        }
        return productNoticeRelId;
    }

    public void setProductNoticeRelId(LongFilter productNoticeRelId) {
        this.productNoticeRelId = productNoticeRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductNoticeCriteria that = (ProductNoticeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(contentFileUrl, that.contentFileUrl) &&
            Objects.equals(priorityDisplay, that.priorityDisplay) &&
            Objects.equals(allProductDisplay, that.allProductDisplay) &&
            Objects.equals(target, that.target) &&
            Objects.equals(enableDisplayDate, that.enableDisplayDate) &&
            Objects.equals(displayDateFrom, that.displayDateFrom) &&
            Objects.equals(displayDateTo, that.displayDateTo) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productNoticeRelId, that.productNoticeRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            type,
            contentFileUrl,
            priorityDisplay,
            allProductDisplay,
            target,
            enableDisplayDate,
            displayDateFrom,
            displayDateTo,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productNoticeRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNoticeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (contentFileUrl != null ? "contentFileUrl=" + contentFileUrl + ", " : "") +
            (priorityDisplay != null ? "priorityDisplay=" + priorityDisplay + ", " : "") +
            (allProductDisplay != null ? "allProductDisplay=" + allProductDisplay + ", " : "") +
            (target != null ? "target=" + target + ", " : "") +
            (enableDisplayDate != null ? "enableDisplayDate=" + enableDisplayDate + ", " : "") +
            (displayDateFrom != null ? "displayDateFrom=" + displayDateFrom + ", " : "") +
            (displayDateTo != null ? "displayDateTo=" + displayDateTo + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productNoticeRelId != null ? "productNoticeRelId=" + productNoticeRelId + ", " : "") +
            "}";
    }
}
