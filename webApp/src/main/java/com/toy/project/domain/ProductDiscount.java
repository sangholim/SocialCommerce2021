package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductDiscount.
 */
@Entity
@Table(name = "product_discount")
public class ProductDiscount extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Size(max = 50)
    @Column(name = "discount_device", length = 50)
    private String discountDevice;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "discount_price_unit")
    private String discountPriceUnit;

    @Column(name = "use_discount_date")
    private Boolean useDiscountDate;

    @Column(name = "discount_date_from")
    private Instant discountDateFrom;

    @Column(name = "discount_date_to")
    private Instant discountDateTo;

    @Column(name = "reduce_price")
    private String reducePrice;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(
        value = {
            "productDiscounts",
            "productMappings",
            "productOptions",
            "productAddOptions",
            "productInputOptions",
            "productFaqs",
            "productAnnounces",
            "productAddImages",
            "productLabels",
            "productTemplates",
            "productCategories",
            "productNoticeManage",
            "productClazzAuthor",
            "productStore",
        },
        allowSetters = true
    )
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscountDevice() {
        return this.discountDevice;
    }

    public void setDiscountDevice(String discountDevice) {
        this.discountDevice = discountDevice;
    }

    public Long getDiscountPrice() {
        return this.discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPriceUnit() {
        return this.discountPriceUnit;
    }

    public void setDiscountPriceUnit(String discountPriceUnit) {
        this.discountPriceUnit = discountPriceUnit;
    }

    public Boolean getUseDiscountDate() {
        return useDiscountDate;
    }

    public void setUseDiscountDate(Boolean useDiscountDate) {
        this.useDiscountDate = useDiscountDate;
    }

    public Instant getDiscountDateFrom() {
        return this.discountDateFrom;
    }

    public void setDiscountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public Instant getDiscountDateTo() {
        return this.discountDateTo;
    }

    public void setDiscountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
    }

    public String getReducePrice() {
        return this.reducePrice;
    }

    public void setReducePrice(String reducePrice) {
        this.reducePrice = reducePrice;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDiscount)) {
            return false;
        }
        return id != null && id.equals(((ProductDiscount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ProductDiscount{" +
            "id=" + id +
            ", productId=" + productId +
            ", discountDevice='" + discountDevice + '\'' +
            ", discountPrice=" + discountPrice +
            ", discountPriceUnit='" + discountPriceUnit + '\'' +
            ", useDiscountDate=" + useDiscountDate +
            ", discountDateFrom=" + discountDateFrom +
            ", discountDateTo=" + discountDateTo +
            ", activated=" + activated +
            ", reducePrice='" + reducePrice + '\'' +
            ", product=" + product +
            '}';
    }
}
