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
 * Criteria class for the {@link com.toy.project.domain.Clazz} entity. This class is used
 * in {@link com.toy.project.web.rest.ClazzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clazzes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClazzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter type;

    private StringFilter mainImageFileUrl;

    private StringFilter level;

    private BooleanFilter enableLecture;

    private BooleanFilter freeLecture;

    private LongFilter priceLecture;

    private StringFilter priceUnitLecture;

    private InstantFilter lectureStartDateFrom;

    private IntegerFilter lectureInterval;

    private StringFilter lecturer;

    private IntegerFilter calculation;

    private BooleanFilter isView;

    private BooleanFilter isSell;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productClazzRelId;

    public ClazzCriteria() {}

    public ClazzCriteria(ClazzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.mainImageFileUrl = other.mainImageFileUrl == null ? null : other.mainImageFileUrl.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.enableLecture = other.enableLecture == null ? null : other.enableLecture.copy();
        this.freeLecture = other.freeLecture == null ? null : other.freeLecture.copy();
        this.priceLecture = other.priceLecture == null ? null : other.priceLecture.copy();
        this.priceUnitLecture = other.priceUnitLecture == null ? null : other.priceUnitLecture.copy();
        this.lectureStartDateFrom = other.lectureStartDateFrom == null ? null : other.lectureStartDateFrom.copy();
        this.lectureInterval = other.lectureInterval == null ? null : other.lectureInterval.copy();
        this.lecturer = other.lecturer == null ? null : other.lecturer.copy();
        this.calculation = other.calculation == null ? null : other.calculation.copy();
        this.isView = other.isView == null ? null : other.isView.copy();
        this.isSell = other.isSell == null ? null : other.isSell.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productClazzRelId = other.productClazzRelId == null ? null : other.productClazzRelId.copy();
    }

    @Override
    public ClazzCriteria copy() {
        return new ClazzCriteria(this);
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

    public StringFilter getMainImageFileUrl() {
        return mainImageFileUrl;
    }

    public StringFilter mainImageFileUrl() {
        if (mainImageFileUrl == null) {
            mainImageFileUrl = new StringFilter();
        }
        return mainImageFileUrl;
    }

    public void setMainImageFileUrl(StringFilter mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public StringFilter getLevel() {
        return level;
    }

    public StringFilter level() {
        if (level == null) {
            level = new StringFilter();
        }
        return level;
    }

    public void setLevel(StringFilter level) {
        this.level = level;
    }

    public BooleanFilter getEnableLecture() {
        return enableLecture;
    }

    public BooleanFilter enableLecture() {
        if (enableLecture == null) {
            enableLecture = new BooleanFilter();
        }
        return enableLecture;
    }

    public void setEnableLecture(BooleanFilter enableLecture) {
        this.enableLecture = enableLecture;
    }

    public BooleanFilter getFreeLecture() {
        return freeLecture;
    }

    public BooleanFilter freeLecture() {
        if (freeLecture == null) {
            freeLecture = new BooleanFilter();
        }
        return freeLecture;
    }

    public void setFreeLecture(BooleanFilter freeLecture) {
        this.freeLecture = freeLecture;
    }

    public LongFilter getPriceLecture() {
        return priceLecture;
    }

    public LongFilter priceLecture() {
        if (priceLecture == null) {
            priceLecture = new LongFilter();
        }
        return priceLecture;
    }

    public void setPriceLecture(LongFilter priceLecture) {
        this.priceLecture = priceLecture;
    }

    public StringFilter getPriceUnitLecture() {
        return priceUnitLecture;
    }

    public StringFilter priceUnitLecture() {
        if (priceUnitLecture == null) {
            priceUnitLecture = new StringFilter();
        }
        return priceUnitLecture;
    }

    public void setPriceUnitLecture(StringFilter priceUnitLecture) {
        this.priceUnitLecture = priceUnitLecture;
    }

    public InstantFilter getLectureStartDateFrom() {
        return lectureStartDateFrom;
    }

    public InstantFilter lectureStartDateFrom() {
        if (lectureStartDateFrom == null) {
            lectureStartDateFrom = new InstantFilter();
        }
        return lectureStartDateFrom;
    }

    public void setLectureStartDateFrom(InstantFilter lectureStartDateFrom) {
        this.lectureStartDateFrom = lectureStartDateFrom;
    }

    public IntegerFilter getLectureInterval() {
        return lectureInterval;
    }

    public IntegerFilter lectureInterval() {
        if (lectureInterval == null) {
            lectureInterval = new IntegerFilter();
        }
        return lectureInterval;
    }

    public void setLectureInterval(IntegerFilter lectureInterval) {
        this.lectureInterval = lectureInterval;
    }

    public StringFilter getLecturer() {
        return lecturer;
    }

    public StringFilter lecturer() {
        if (lecturer == null) {
            lecturer = new StringFilter();
        }
        return lecturer;
    }

    public void setLecturer(StringFilter lecturer) {
        this.lecturer = lecturer;
    }

    public IntegerFilter getCalculation() {
        return calculation;
    }

    public IntegerFilter calculation() {
        if (calculation == null) {
            calculation = new IntegerFilter();
        }
        return calculation;
    }

    public void setCalculation(IntegerFilter calculation) {
        this.calculation = calculation;
    }

    public BooleanFilter getIsView() {
        return isView;
    }

    public BooleanFilter isView() {
        if (isView == null) {
            isView = new BooleanFilter();
        }
        return isView;
    }

    public void setIsView(BooleanFilter isView) {
        this.isView = isView;
    }

    public BooleanFilter getIsSell() {
        return isSell;
    }

    public BooleanFilter isSell() {
        if (isSell == null) {
            isSell = new BooleanFilter();
        }
        return isSell;
    }

    public void setIsSell(BooleanFilter isSell) {
        this.isSell = isSell;
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

    public LongFilter getProductClazzRelId() {
        return productClazzRelId;
    }

    public LongFilter productClazzRelId() {
        if (productClazzRelId == null) {
            productClazzRelId = new LongFilter();
        }
        return productClazzRelId;
    }

    public void setProductClazzRelId(LongFilter productClazzRelId) {
        this.productClazzRelId = productClazzRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClazzCriteria that = (ClazzCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(mainImageFileUrl, that.mainImageFileUrl) &&
            Objects.equals(level, that.level) &&
            Objects.equals(enableLecture, that.enableLecture) &&
            Objects.equals(freeLecture, that.freeLecture) &&
            Objects.equals(priceLecture, that.priceLecture) &&
            Objects.equals(priceUnitLecture, that.priceUnitLecture) &&
            Objects.equals(lectureStartDateFrom, that.lectureStartDateFrom) &&
            Objects.equals(lectureInterval, that.lectureInterval) &&
            Objects.equals(lecturer, that.lecturer) &&
            Objects.equals(calculation, that.calculation) &&
            Objects.equals(isView, that.isView) &&
            Objects.equals(isSell, that.isSell) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productClazzRelId, that.productClazzRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            type,
            mainImageFileUrl,
            level,
            enableLecture,
            freeLecture,
            priceLecture,
            priceUnitLecture,
            lectureStartDateFrom,
            lectureInterval,
            lecturer,
            calculation,
            isView,
            isSell,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productClazzRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (mainImageFileUrl != null ? "mainImageFileUrl=" + mainImageFileUrl + ", " : "") +
            (level != null ? "level=" + level + ", " : "") +
            (enableLecture != null ? "enableLecture=" + enableLecture + ", " : "") +
            (freeLecture != null ? "freeLecture=" + freeLecture + ", " : "") +
            (priceLecture != null ? "priceLecture=" + priceLecture + ", " : "") +
            (priceUnitLecture != null ? "priceUnitLecture=" + priceUnitLecture + ", " : "") +
            (lectureStartDateFrom != null ? "lectureStartDateFrom=" + lectureStartDateFrom + ", " : "") +
            (lectureInterval != null ? "lectureInterval=" + lectureInterval + ", " : "") +
            (lecturer != null ? "lecturer=" + lecturer + ", " : "") +
            (calculation != null ? "calculation=" + calculation + ", " : "") +
            (isView != null ? "isView=" + isView + ", " : "") +
            (isSell != null ? "isSell=" + isSell + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productClazzRelId != null ? "productClazzRelId=" + productClazzRelId + ", " : "") +
            "}";
    }
}
