package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductStore.
 */
@Entity
@Table(name = "product_store")
public class ProductStore extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "brand_id")
    private Long brandId;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

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

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "productStore")
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
    @JoinColumn(name = "vendor_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productStores" }, allowSetters = true)
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productStores" }, allowSetters = true)
    private Brand brand;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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
            this.products.forEach(i -> i.setProductStore(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProductStore(this));
        }
        this.products = products;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStore)) {
            return false;
        }
        return id != null && id.equals(((ProductStore) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ProductStore{" +
            "id=" + id +
            ", vendorId=" + vendorId +
            ", brandId=" + brandId +
            ", type='" + type + '\'' +
            ", useCalculation=" + useCalculation +
            ", calculation=" + calculation +
            ", calculationUnit='" + calculationUnit + '\'' +
            ", useCalculationDate=" + useCalculationDate +
            ", calculationDateFrom=" + calculationDateFrom +
            ", calculationDateTo=" + calculationDateTo +
            ", activated=" + activated +
            ", products=" + products +
            ", vendor=" + vendor +
            ", brand=" + brand +
            '}';
    }
}
