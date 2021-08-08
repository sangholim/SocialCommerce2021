package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductClazzAuthor.
 */
@Entity
@Table(name = "product_clazz_author")
public class ProductClazzAuthor extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clazz_id")
    private Long clazzId;

    @Column(name = "author_id")
    private Long authorId;

    @Size(max = 50)
    @Column(name = "class_type", length = 50)
    private String classType;

    @Column(name = "use_calculation")
    private Boolean useCalculation;

    @Column(name = "calculation")
    private Integer calculation;

    @Size(max = 50)
    @Column(name = "calculation_unit", length = 50)
    private String calculationUnit;

    @Column(name = "use_calculation_date")
    private Boolean useCalculationDate;

    @Column(name = "calculation_date_from")
    private Instant calculationDateFrom;

    @Column(name = "calculation_date_to")
    private Instant calculationDateTo;

    @Column(name = "use_discount")
    private Boolean useDiscount;

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

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productClazzAuthor")
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
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "clazz_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productClazzAuthors", "clazzChapters", "author" }, allowSetters = true)
    private Clazz clazz;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productClazzAuthors", "clazzes" }, allowSetters = true)
    private Author author;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassType() {
        return this.classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public String getCalculationUnit() {
        return this.calculationUnit;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
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

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setProductClazzAuthor(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProductClazzAuthor(this));
        }
        this.products = products;
    }

    public Clazz getClazz() {
        return this.clazz;
    }

    public ProductClazzAuthor clazz(Clazz clazz) {
        this.setClazz(clazz);
        return this;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Author getAuthor() {
        return this.author;
    }

    public ProductClazzAuthor author(Author author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Boolean getUseCalculation() {
        return useCalculation;
    }

    public void setUseCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
    }

    public Boolean getUseCalculationDate() {
        return useCalculationDate;
    }

    public void setUseCalculationDate(Boolean useCalculationDate) {
        this.useCalculationDate = useCalculationDate;
    }

    public Boolean getUseDiscount() {
        return useDiscount;
    }

    public void setUseDiscount(Boolean useDiscount) {
        this.useDiscount = useDiscount;
    }

    public Boolean getUseDiscountDate() {
        return useDiscountDate;
    }

    public void setUseDiscountDate(Boolean useDiscountDate) {
        this.useDiscountDate = useDiscountDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductClazzAuthor)) {
            return false;
        }
        return id != null && id.equals(((ProductClazzAuthor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductClazzAuthor{" +
            "id=" + getId() +
            ", clazzId='" + getClazzId() + "'" +
            ", authorId='" + getAuthorId() + "'" +
            ", classType='" + getClassType() + "'" +
            ", useCalculation='" + getUseCalculation() + "'" +
            ", calculation=" + getCalculation() +
            ", calculationUnit='" + getCalculationUnit() + "'" +
            ", useCalculationDate='" + getUseCalculationDate() + "'" +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", useDiscount='" + getUseDiscount() + "'" +
            ", discountPrice=" + getDiscountPrice() +
            ", discountPriceUnit='" + getDiscountPriceUnit() + "'" +
            ", useDiscountDate='" + getUseDiscountDate() + "'" +
            ", discountDateFrom='" + getDiscountDateFrom() + "'" +
            ", discountDateTo='" + getDiscountDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
