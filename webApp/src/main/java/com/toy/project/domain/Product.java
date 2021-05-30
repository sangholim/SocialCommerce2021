package com.toy.project.domain;

import com.toy.project.domain.enumeration.DifficultType;
import com.toy.project.domain.enumeration.ProductType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private DifficultType difficulty;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "owner")
    private String owner;

    @Column(name = "regdate")
    private Instant regdate;

    @Column(name = "price_regular")
    private Integer priceRegular;

    @Column(name = "is_use_discount")
    private Integer isUseDiscount;

    @Column(name = "discount_unit")
    private String discountUnit;

    @Column(name = "discount_value")
    private Integer discountValue;

    @Column(name = "discount_startdate")
    private Instant discountStartdate;

    @Column(name = "discount_interval")
    private Integer discountInterval;

    @Column(name = "video")
    private String video;

    @Column(name = "startdate")
    private Instant startdate;

    @Column(name = "prepare_resource")
    private String prepareResource;

    @Column(name = "introduce_resource")
    private String introduceResource;

    @Column(name = "shipping_resource")
    private String shippingResource;

    @Column(name = "refund_resource")
    private String refundResource;

    @Column(name = "change_resource")
    private String changeResource;

    @Column(name = "code")
    private String code;

    @Column(name = "installment_month")
    private Integer installmentMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProductType type;

    @Column(name = "number")
    private String number;

    @Column(name = "popular_count")
    private Integer popularCount;

    @Column(name = "review_count")
    private Integer reviewCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DifficultType getDifficulty() {
        return this.difficulty;
    }

    public Product difficulty(DifficultType difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(DifficultType difficulty) {
        this.difficulty = difficulty;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public Product thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOwner() {
        return this.owner;
    }

    public Product owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Instant getRegdate() {
        return this.regdate;
    }

    public Product regdate(Instant regdate) {
        this.regdate = regdate;
        return this;
    }

    public void setRegdate(Instant regdate) {
        this.regdate = regdate;
    }

    public Integer getPriceRegular() {
        return this.priceRegular;
    }

    public Product priceRegular(Integer priceRegular) {
        this.priceRegular = priceRegular;
        return this;
    }

    public void setPriceRegular(Integer priceRegular) {
        this.priceRegular = priceRegular;
    }

    public Integer getIsUseDiscount() {
        return this.isUseDiscount;
    }

    public Product isUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
        return this;
    }

    public void setIsUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    public String getDiscountUnit() {
        return this.discountUnit;
    }

    public Product discountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
        return this;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Integer getDiscountValue() {
        return this.discountValue;
    }

    public Product discountValue(Integer discountValue) {
        this.discountValue = discountValue;
        return this;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }

    public Instant getDiscountStartdate() {
        return this.discountStartdate;
    }

    public Product discountStartdate(Instant discountStartdate) {
        this.discountStartdate = discountStartdate;
        return this;
    }

    public void setDiscountStartdate(Instant discountStartdate) {
        this.discountStartdate = discountStartdate;
    }

    public Integer getDiscountInterval() {
        return this.discountInterval;
    }

    public Product discountInterval(Integer discountInterval) {
        this.discountInterval = discountInterval;
        return this;
    }

    public void setDiscountInterval(Integer discountInterval) {
        this.discountInterval = discountInterval;
    }

    public String getVideo() {
        return this.video;
    }

    public Product video(String video) {
        this.video = video;
        return this;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Instant getStartdate() {
        return this.startdate;
    }

    public Product startdate(Instant startdate) {
        this.startdate = startdate;
        return this;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public String getPrepareResource() {
        return this.prepareResource;
    }

    public Product prepareResource(String prepareResource) {
        this.prepareResource = prepareResource;
        return this;
    }

    public void setPrepareResource(String prepareResource) {
        this.prepareResource = prepareResource;
    }

    public String getIntroduceResource() {
        return this.introduceResource;
    }

    public Product introduceResource(String introduceResource) {
        this.introduceResource = introduceResource;
        return this;
    }

    public void setIntroduceResource(String introduceResource) {
        this.introduceResource = introduceResource;
    }

    public String getShippingResource() {
        return this.shippingResource;
    }

    public Product shippingResource(String shippingResource) {
        this.shippingResource = shippingResource;
        return this;
    }

    public void setShippingResource(String shippingResource) {
        this.shippingResource = shippingResource;
    }

    public String getRefundResource() {
        return this.refundResource;
    }

    public Product refundResource(String refundResource) {
        this.refundResource = refundResource;
        return this;
    }

    public void setRefundResource(String refundResource) {
        this.refundResource = refundResource;
    }

    public String getChangeResource() {
        return this.changeResource;
    }

    public Product changeResource(String changeResource) {
        this.changeResource = changeResource;
        return this;
    }

    public void setChangeResource(String changeResource) {
        this.changeResource = changeResource;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getInstallmentMonth() {
        return this.installmentMonth;
    }

    public Product installmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
        return this;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public ProductType getType() {
        return this.type;
    }

    public Product type(ProductType type) {
        this.type = type;
        return this;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getNumber() {
        return this.number;
    }

    public Product number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPopularCount() {
        return this.popularCount;
    }

    public Product popularCount(Integer popularCount) {
        this.popularCount = popularCount;
        return this;
    }

    public void setPopularCount(Integer popularCount) {
        this.popularCount = popularCount;
    }

    public Integer getReviewCount() {
        return this.reviewCount;
    }

    public Product reviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", owner='" + getOwner() + "'" +
            ", regdate='" + getRegdate() + "'" +
            ", priceRegular=" + getPriceRegular() +
            ", isUseDiscount=" + getIsUseDiscount() +
            ", discountUnit='" + getDiscountUnit() + "'" +
            ", discountValue=" + getDiscountValue() +
            ", discountStartdate='" + getDiscountStartdate() + "'" +
            ", discountInterval=" + getDiscountInterval() +
            ", video='" + getVideo() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", prepareResource='" + getPrepareResource() + "'" +
            ", introduceResource='" + getIntroduceResource() + "'" +
            ", shippingResource='" + getShippingResource() + "'" +
            ", refundResource='" + getRefundResource() + "'" +
            ", changeResource='" + getChangeResource() + "'" +
            ", code='" + getCode() + "'" +
            ", installmentMonth=" + getInstallmentMonth() +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            ", popularCount=" + getPopularCount() +
            ", reviewCount=" + getReviewCount() +
            "}";
    }
}
