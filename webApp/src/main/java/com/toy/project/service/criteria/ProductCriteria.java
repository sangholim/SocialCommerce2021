package com.toy.project.service.criteria;

import com.toy.project.domain.enumeration.DifficultType;
import com.toy.project.domain.enumeration.ProductType;
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
 * Criteria class for the {@link com.toy.project.domain.Product} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DifficultType
     */
    public static class DifficultTypeFilter extends Filter<DifficultType> {

        public DifficultTypeFilter() {}

        public DifficultTypeFilter(DifficultTypeFilter filter) {
            super(filter);
        }

        @Override
        public DifficultTypeFilter copy() {
            return new DifficultTypeFilter(this);
        }
    }

    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {

        public ProductTypeFilter() {}

        public ProductTypeFilter(ProductTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProductTypeFilter copy() {
            return new ProductTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private DifficultTypeFilter difficulty;

    private StringFilter thumbnail;

    private StringFilter owner;

    private InstantFilter regdate;

    private IntegerFilter priceRegular;

    private IntegerFilter isUseDiscount;

    private StringFilter discountUnit;

    private IntegerFilter discountValue;

    private InstantFilter discountStartdate;

    private IntegerFilter discountInterval;

    private StringFilter video;

    private InstantFilter startdate;

    private StringFilter prepareResource;

    private StringFilter introduceResource;

    private StringFilter shippingResource;

    private StringFilter refundResource;

    private StringFilter changeResource;

    private StringFilter code;

    private IntegerFilter installmentMonth;

    private ProductTypeFilter type;

    private StringFilter number;

    private IntegerFilter popularCount;

    private IntegerFilter reviewCount;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.difficulty = other.difficulty == null ? null : other.difficulty.copy();
        this.thumbnail = other.thumbnail == null ? null : other.thumbnail.copy();
        this.owner = other.owner == null ? null : other.owner.copy();
        this.regdate = other.regdate == null ? null : other.regdate.copy();
        this.priceRegular = other.priceRegular == null ? null : other.priceRegular.copy();
        this.isUseDiscount = other.isUseDiscount == null ? null : other.isUseDiscount.copy();
        this.discountUnit = other.discountUnit == null ? null : other.discountUnit.copy();
        this.discountValue = other.discountValue == null ? null : other.discountValue.copy();
        this.discountStartdate = other.discountStartdate == null ? null : other.discountStartdate.copy();
        this.discountInterval = other.discountInterval == null ? null : other.discountInterval.copy();
        this.video = other.video == null ? null : other.video.copy();
        this.startdate = other.startdate == null ? null : other.startdate.copy();
        this.prepareResource = other.prepareResource == null ? null : other.prepareResource.copy();
        this.introduceResource = other.introduceResource == null ? null : other.introduceResource.copy();
        this.shippingResource = other.shippingResource == null ? null : other.shippingResource.copy();
        this.refundResource = other.refundResource == null ? null : other.refundResource.copy();
        this.changeResource = other.changeResource == null ? null : other.changeResource.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.installmentMonth = other.installmentMonth == null ? null : other.installmentMonth.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.popularCount = other.popularCount == null ? null : other.popularCount.copy();
        this.reviewCount = other.reviewCount == null ? null : other.reviewCount.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public DifficultTypeFilter getDifficulty() {
        return difficulty;
    }

    public DifficultTypeFilter difficulty() {
        if (difficulty == null) {
            difficulty = new DifficultTypeFilter();
        }
        return difficulty;
    }

    public void setDifficulty(DifficultTypeFilter difficulty) {
        this.difficulty = difficulty;
    }

    public StringFilter getThumbnail() {
        return thumbnail;
    }

    public StringFilter thumbnail() {
        if (thumbnail == null) {
            thumbnail = new StringFilter();
        }
        return thumbnail;
    }

    public void setThumbnail(StringFilter thumbnail) {
        this.thumbnail = thumbnail;
    }

    public StringFilter getOwner() {
        return owner;
    }

    public StringFilter owner() {
        if (owner == null) {
            owner = new StringFilter();
        }
        return owner;
    }

    public void setOwner(StringFilter owner) {
        this.owner = owner;
    }

    public InstantFilter getRegdate() {
        return regdate;
    }

    public InstantFilter regdate() {
        if (regdate == null) {
            regdate = new InstantFilter();
        }
        return regdate;
    }

    public void setRegdate(InstantFilter regdate) {
        this.regdate = regdate;
    }

    public IntegerFilter getPriceRegular() {
        return priceRegular;
    }

    public IntegerFilter priceRegular() {
        if (priceRegular == null) {
            priceRegular = new IntegerFilter();
        }
        return priceRegular;
    }

    public void setPriceRegular(IntegerFilter priceRegular) {
        this.priceRegular = priceRegular;
    }

    public IntegerFilter getIsUseDiscount() {
        return isUseDiscount;
    }

    public IntegerFilter isUseDiscount() {
        if (isUseDiscount == null) {
            isUseDiscount = new IntegerFilter();
        }
        return isUseDiscount;
    }

    public void setIsUseDiscount(IntegerFilter isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    public StringFilter getDiscountUnit() {
        return discountUnit;
    }

    public StringFilter discountUnit() {
        if (discountUnit == null) {
            discountUnit = new StringFilter();
        }
        return discountUnit;
    }

    public void setDiscountUnit(StringFilter discountUnit) {
        this.discountUnit = discountUnit;
    }

    public IntegerFilter getDiscountValue() {
        return discountValue;
    }

    public IntegerFilter discountValue() {
        if (discountValue == null) {
            discountValue = new IntegerFilter();
        }
        return discountValue;
    }

    public void setDiscountValue(IntegerFilter discountValue) {
        this.discountValue = discountValue;
    }

    public InstantFilter getDiscountStartdate() {
        return discountStartdate;
    }

    public InstantFilter discountStartdate() {
        if (discountStartdate == null) {
            discountStartdate = new InstantFilter();
        }
        return discountStartdate;
    }

    public void setDiscountStartdate(InstantFilter discountStartdate) {
        this.discountStartdate = discountStartdate;
    }

    public IntegerFilter getDiscountInterval() {
        return discountInterval;
    }

    public IntegerFilter discountInterval() {
        if (discountInterval == null) {
            discountInterval = new IntegerFilter();
        }
        return discountInterval;
    }

    public void setDiscountInterval(IntegerFilter discountInterval) {
        this.discountInterval = discountInterval;
    }

    public StringFilter getVideo() {
        return video;
    }

    public StringFilter video() {
        if (video == null) {
            video = new StringFilter();
        }
        return video;
    }

    public void setVideo(StringFilter video) {
        this.video = video;
    }

    public InstantFilter getStartdate() {
        return startdate;
    }

    public InstantFilter startdate() {
        if (startdate == null) {
            startdate = new InstantFilter();
        }
        return startdate;
    }

    public void setStartdate(InstantFilter startdate) {
        this.startdate = startdate;
    }

    public StringFilter getPrepareResource() {
        return prepareResource;
    }

    public StringFilter prepareResource() {
        if (prepareResource == null) {
            prepareResource = new StringFilter();
        }
        return prepareResource;
    }

    public void setPrepareResource(StringFilter prepareResource) {
        this.prepareResource = prepareResource;
    }

    public StringFilter getIntroduceResource() {
        return introduceResource;
    }

    public StringFilter introduceResource() {
        if (introduceResource == null) {
            introduceResource = new StringFilter();
        }
        return introduceResource;
    }

    public void setIntroduceResource(StringFilter introduceResource) {
        this.introduceResource = introduceResource;
    }

    public StringFilter getShippingResource() {
        return shippingResource;
    }

    public StringFilter shippingResource() {
        if (shippingResource == null) {
            shippingResource = new StringFilter();
        }
        return shippingResource;
    }

    public void setShippingResource(StringFilter shippingResource) {
        this.shippingResource = shippingResource;
    }

    public StringFilter getRefundResource() {
        return refundResource;
    }

    public StringFilter refundResource() {
        if (refundResource == null) {
            refundResource = new StringFilter();
        }
        return refundResource;
    }

    public void setRefundResource(StringFilter refundResource) {
        this.refundResource = refundResource;
    }

    public StringFilter getChangeResource() {
        return changeResource;
    }

    public StringFilter changeResource() {
        if (changeResource == null) {
            changeResource = new StringFilter();
        }
        return changeResource;
    }

    public void setChangeResource(StringFilter changeResource) {
        this.changeResource = changeResource;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getInstallmentMonth() {
        return installmentMonth;
    }

    public IntegerFilter installmentMonth() {
        if (installmentMonth == null) {
            installmentMonth = new IntegerFilter();
        }
        return installmentMonth;
    }

    public void setInstallmentMonth(IntegerFilter installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public ProductTypeFilter getType() {
        return type;
    }

    public ProductTypeFilter type() {
        if (type == null) {
            type = new ProductTypeFilter();
        }
        return type;
    }

    public void setType(ProductTypeFilter type) {
        this.type = type;
    }

    public StringFilter getNumber() {
        return number;
    }

    public StringFilter number() {
        if (number == null) {
            number = new StringFilter();
        }
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public IntegerFilter getPopularCount() {
        return popularCount;
    }

    public IntegerFilter popularCount() {
        if (popularCount == null) {
            popularCount = new IntegerFilter();
        }
        return popularCount;
    }

    public void setPopularCount(IntegerFilter popularCount) {
        this.popularCount = popularCount;
    }

    public IntegerFilter getReviewCount() {
        return reviewCount;
    }

    public IntegerFilter reviewCount() {
        if (reviewCount == null) {
            reviewCount = new IntegerFilter();
        }
        return reviewCount;
    }

    public void setReviewCount(IntegerFilter reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(difficulty, that.difficulty) &&
            Objects.equals(thumbnail, that.thumbnail) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(regdate, that.regdate) &&
            Objects.equals(priceRegular, that.priceRegular) &&
            Objects.equals(isUseDiscount, that.isUseDiscount) &&
            Objects.equals(discountUnit, that.discountUnit) &&
            Objects.equals(discountValue, that.discountValue) &&
            Objects.equals(discountStartdate, that.discountStartdate) &&
            Objects.equals(discountInterval, that.discountInterval) &&
            Objects.equals(video, that.video) &&
            Objects.equals(startdate, that.startdate) &&
            Objects.equals(prepareResource, that.prepareResource) &&
            Objects.equals(introduceResource, that.introduceResource) &&
            Objects.equals(shippingResource, that.shippingResource) &&
            Objects.equals(refundResource, that.refundResource) &&
            Objects.equals(changeResource, that.changeResource) &&
            Objects.equals(code, that.code) &&
            Objects.equals(installmentMonth, that.installmentMonth) &&
            Objects.equals(type, that.type) &&
            Objects.equals(number, that.number) &&
            Objects.equals(popularCount, that.popularCount) &&
            Objects.equals(reviewCount, that.reviewCount)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            difficulty,
            thumbnail,
            owner,
            regdate,
            priceRegular,
            isUseDiscount,
            discountUnit,
            discountValue,
            discountStartdate,
            discountInterval,
            video,
            startdate,
            prepareResource,
            introduceResource,
            shippingResource,
            refundResource,
            changeResource,
            code,
            installmentMonth,
            type,
            number,
            popularCount,
            reviewCount
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (difficulty != null ? "difficulty=" + difficulty + ", " : "") +
            (thumbnail != null ? "thumbnail=" + thumbnail + ", " : "") +
            (owner != null ? "owner=" + owner + ", " : "") +
            (regdate != null ? "regdate=" + regdate + ", " : "") +
            (priceRegular != null ? "priceRegular=" + priceRegular + ", " : "") +
            (isUseDiscount != null ? "isUseDiscount=" + isUseDiscount + ", " : "") +
            (discountUnit != null ? "discountUnit=" + discountUnit + ", " : "") +
            (discountValue != null ? "discountValue=" + discountValue + ", " : "") +
            (discountStartdate != null ? "discountStartdate=" + discountStartdate + ", " : "") +
            (discountInterval != null ? "discountInterval=" + discountInterval + ", " : "") +
            (video != null ? "video=" + video + ", " : "") +
            (startdate != null ? "startdate=" + startdate + ", " : "") +
            (prepareResource != null ? "prepareResource=" + prepareResource + ", " : "") +
            (introduceResource != null ? "introduceResource=" + introduceResource + ", " : "") +
            (shippingResource != null ? "shippingResource=" + shippingResource + ", " : "") +
            (refundResource != null ? "refundResource=" + refundResource + ", " : "") +
            (changeResource != null ? "changeResource=" + changeResource + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (installmentMonth != null ? "installmentMonth=" + installmentMonth + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (number != null ? "number=" + number + ", " : "") +
            (popularCount != null ? "popularCount=" + popularCount + ", " : "") +
            (reviewCount != null ? "reviewCount=" + reviewCount + ", " : "") +
            "}";
    }
}
