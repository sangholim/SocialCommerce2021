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
 * Criteria class for the {@link com.toy.project.domain.ClazzChapterVideo} entity. This class is used
 * in {@link com.toy.project.web.rest.ClazzChapterVideoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clazz-chapter-videos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClazzChapterVideoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter thumbFileUrl;

    private StringFilter originalLinkUrl;

    private StringFilter time;

    private IntegerFilter order;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter clazzChapterId;

    public ClazzChapterVideoCriteria() {}

    public ClazzChapterVideoCriteria(ClazzChapterVideoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.thumbFileUrl = other.thumbFileUrl == null ? null : other.thumbFileUrl.copy();
        this.originalLinkUrl = other.originalLinkUrl == null ? null : other.originalLinkUrl.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.clazzChapterId = other.clazzChapterId == null ? null : other.clazzChapterId.copy();
    }

    @Override
    public ClazzChapterVideoCriteria copy() {
        return new ClazzChapterVideoCriteria(this);
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

    public StringFilter getThumbFileUrl() {
        return thumbFileUrl;
    }

    public StringFilter thumbFileUrl() {
        if (thumbFileUrl == null) {
            thumbFileUrl = new StringFilter();
        }
        return thumbFileUrl;
    }

    public void setThumbFileUrl(StringFilter thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
    }

    public StringFilter getOriginalLinkUrl() {
        return originalLinkUrl;
    }

    public StringFilter originalLinkUrl() {
        if (originalLinkUrl == null) {
            originalLinkUrl = new StringFilter();
        }
        return originalLinkUrl;
    }

    public void setOriginalLinkUrl(StringFilter originalLinkUrl) {
        this.originalLinkUrl = originalLinkUrl;
    }

    public StringFilter getTime() {
        return time;
    }

    public StringFilter time() {
        if (time == null) {
            time = new StringFilter();
        }
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public IntegerFilter order() {
        if (order == null) {
            order = new IntegerFilter();
        }
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
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

    public LongFilter getClazzChapterId() {
        return clazzChapterId;
    }

    public LongFilter clazzChapterId() {
        if (clazzChapterId == null) {
            clazzChapterId = new LongFilter();
        }
        return clazzChapterId;
    }

    public void setClazzChapterId(LongFilter clazzChapterId) {
        this.clazzChapterId = clazzChapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClazzChapterVideoCriteria that = (ClazzChapterVideoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(thumbFileUrl, that.thumbFileUrl) &&
            Objects.equals(originalLinkUrl, that.originalLinkUrl) &&
            Objects.equals(time, that.time) &&
            Objects.equals(order, that.order) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(clazzChapterId, that.clazzChapterId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            thumbFileUrl,
            originalLinkUrl,
            time,
            order,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            clazzChapterId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzChapterVideoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (thumbFileUrl != null ? "thumbFileUrl=" + thumbFileUrl + ", " : "") +
            (originalLinkUrl != null ? "originalLinkUrl=" + originalLinkUrl + ", " : "") +
            (time != null ? "time=" + time + ", " : "") +
            (order != null ? "order=" + order + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (clazzChapterId != null ? "clazzChapterId=" + clazzChapterId + ", " : "") +
            "}";
    }
}
