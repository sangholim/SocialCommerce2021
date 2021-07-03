package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Product.
 */
@Entity
@NamedEntityGraph(
    name = "graph.product.productCategory",
    attributeNodes = {
        @NamedAttributeNode(value = "productCategoryRels", subgraph = "productCategoryRels"),
        @NamedAttributeNode(value = "productLabelRels", subgraph = "productLabelRels"),
        @NamedAttributeNode(value = "productMappingRels", subgraph = "productMappingRels"),
        @NamedAttributeNode(value = "productTemplateRels", subgraph = "productTemplateRels"),
        @NamedAttributeNode(value = "productViewRels", subgraph = "productViewRels"),
        @NamedAttributeNode(value = "productNoticeRels", subgraph = "productNoticeRels"),
        @NamedAttributeNode(value = "productShippingRels", subgraph = "productShippingRels"),
        @NamedAttributeNode(value = "productOptionRels", subgraph = "productOptionRels"),
        @NamedAttributeNode(value = "productClazzRels", subgraph = "productClazzRels"),
        @NamedAttributeNode(value = "productStoreRels", subgraph = "productStoreRels"),
    },
    subgraphs = {
        @NamedSubgraph(name = "productCategoryRels", attributeNodes = @NamedAttributeNode("productCategory")),
        @NamedSubgraph(name = "productLabelRels", attributeNodes = @NamedAttributeNode("productLabel")),
        @NamedSubgraph(name = "productMappingRels", attributeNodes = @NamedAttributeNode("productMapping")),
        @NamedSubgraph(name = "productTemplateRels", attributeNodes = @NamedAttributeNode("productTemplate")),
        @NamedSubgraph(name = "productViewRels", attributeNodes = @NamedAttributeNode("productView")),
        @NamedSubgraph(name = "productNoticeRels", attributeNodes = @NamedAttributeNode("productNotice")),
        @NamedSubgraph(name = "productShippingRels", attributeNodes = @NamedAttributeNode("productShipping")),
        @NamedSubgraph(name = "productOptionRels", attributeNodes = @NamedAttributeNode("productOption")),
        @NamedSubgraph(name = "productClazzRels", attributeNodes = @NamedAttributeNode("clazz")),
        @NamedSubgraph(name = "productStoreRels", attributeNodes = @NamedAttributeNode("store")),
    }
)
@Table(name = "product")
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "calculation")
    private String calculation;

    @Column(name = "calculation_date_from")
    private Instant calculationDateFrom;

    @Column(name = "calculation_date_to")
    private Instant calculationDateTo;

    @Column(name = "price")
    private Integer price;

    @Column(name = "all_price_unit")
    private String allPriceUnit;

    @Column(name = "discount")
    private String discount;

    @Column(name = "discount_price")
    private String discountPrice;

    @Column(name = "discount_unit")
    private String discountUnit;

    @Column(name = "discount_date_from")
    private Instant discountDateFrom;

    @Column(name = "discount_date_to")
    private Instant discountDateTo;

    @Column(name = "is_installment")
    private Boolean isInstallment;

    @Column(name = "installment_month")
    private Integer installmentMonth;

    @Column(name = "is_sell")
    private Boolean isSell;

    @Column(name = "sell_date_from")
    private Instant sellDateFrom;

    @Column(name = "sell_date_to")
    private Instant sellDateTo;

    @Column(name = "min_purchase_amount")
    private Integer minPurchaseAmount;

    @Column(name = "man_purchase_amount")
    private Integer manPurchaseAmount;

    @Column(name = "main_image_file_url")
    private String mainImageFileUrl;

    @Lob
    @Column(name = "add_image_file_url")
    private String addImageFileUrl;

    @Column(name = "main_video_file_url")
    private String mainVideoFileUrl;

    @Column(name = "description_file_url")
    private String descriptionFileUrl;

    @Column(name = "shipping_type")
    private String shippingType;

    @Column(name = "separate_shipping_price_type")
    private String separateShippingPriceType;

    @Column(name = "default_shipping_price")
    private Integer defaultShippingPrice;

    @Column(name = "free_shipping_price")
    private Integer freeShippingPrice;

    @Column(name = "jeju_shipping_price")
    private Integer jejuShippingPrice;

    @Column(name = "difficult_shipping_price")
    private Integer difficultShippingPrice;

    @Column(name = "refund_shipping_price")
    private Integer refundShippingPrice;

    @Column(name = "exchange_shipping_price")
    private Integer exchangeShippingPrice;

    @Column(name = "exchange_shipping_file_url")
    private String exchangeShippingFileUrl;

    @Column(name = "is_view")
    private Boolean isView;

    @Column(name = "view_reservation_date")
    private Instant viewReservationDate;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productCategory" }, allowSetters = true)
    private Set<ProductCategoryRel> productCategoryRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productLabel" }, allowSetters = true)
    private Set<ProductLabelRel> productLabelRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productMapping" }, allowSetters = true)
    private Set<ProductMappingRel> productMappingRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productView" }, allowSetters = true)
    private Set<ProductViewRel> productViewRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productNotice" }, allowSetters = true)
    private Set<ProductNoticeRel> productNoticeRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productShipping" }, allowSetters = true)
    private Set<ProductShippingRel> productShippingRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productTemplate" }, allowSetters = true)
    private Set<ProductTemplateRel> productTemplateRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productOption" }, allowSetters = true)
    private Set<ProductOptionRel> productOptionRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "clazz" }, allowSetters = true)
    private Set<ProductClazzRel> productClazzRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "store" }, allowSetters = true)
    private Set<ProductStoreRel> productStoreRels = new HashSet<>();

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

    public String getCalculation() {
        return this.calculation;
    }

    public Product calculation(String calculation) {
        this.calculation = calculation;
        return this;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public Product calculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
        return this;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public Product calculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
        return this;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Product price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAllPriceUnit() {
        return this.allPriceUnit;
    }

    public Product allPriceUnit(String allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
        return this;
    }

    public void setAllPriceUnit(String allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
    }

    public String getDiscount() {
        return this.discount;
    }

    public Product discount(String discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return this.discountPrice;
    }

    public Product discountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
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

    public Instant getDiscountDateFrom() {
        return this.discountDateFrom;
    }

    public Product discountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
        return this;
    }

    public void setDiscountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public Instant getDiscountDateTo() {
        return this.discountDateTo;
    }

    public Product discountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
        return this;
    }

    public void setDiscountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
    }

    public Boolean getIsInstallment() {
        return this.isInstallment;
    }

    public Product isInstallment(Boolean isInstallment) {
        this.isInstallment = isInstallment;
        return this;
    }

    public void setIsInstallment(Boolean isInstallment) {
        this.isInstallment = isInstallment;
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

    public Boolean getIsSell() {
        return this.isSell;
    }

    public Product isSell(Boolean isSell) {
        this.isSell = isSell;
        return this;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
    }

    public Instant getSellDateFrom() {
        return this.sellDateFrom;
    }

    public Product sellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
        return this;
    }

    public void setSellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public Instant getSellDateTo() {
        return this.sellDateTo;
    }

    public Product sellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
        return this;
    }

    public void setSellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    public Integer getMinPurchaseAmount() {
        return this.minPurchaseAmount;
    }

    public Product minPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
        return this;
    }

    public void setMinPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getManPurchaseAmount() {
        return this.manPurchaseAmount;
    }

    public Product manPurchaseAmount(Integer manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
        return this;
    }

    public void setManPurchaseAmount(Integer manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
    }

    public String getMainImageFileUrl() {
        return this.mainImageFileUrl;
    }

    public Product mainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
        return this;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getAddImageFileUrl() {
        return this.addImageFileUrl;
    }

    public Product addImageFileUrl(String addImageFileUrl) {
        this.addImageFileUrl = addImageFileUrl;
        return this;
    }

    public void setAddImageFileUrl(String addImageFileUrl) {
        this.addImageFileUrl = addImageFileUrl;
    }

    public String getMainVideoFileUrl() {
        return this.mainVideoFileUrl;
    }

    public Product mainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
        return this;
    }

    public void setMainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
    }

    public String getDescriptionFileUrl() {
        return this.descriptionFileUrl;
    }

    public Product descriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
        return this;
    }

    public void setDescriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
    }

    public String getShippingType() {
        return this.shippingType;
    }

    public Product shippingType(String shippingType) {
        this.shippingType = shippingType;
        return this;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getSeparateShippingPriceType() {
        return this.separateShippingPriceType;
    }

    public Product separateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
        return this;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public Integer getDefaultShippingPrice() {
        return this.defaultShippingPrice;
    }

    public Product defaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
        return this;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return this.freeShippingPrice;
    }

    public Product freeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
        return this;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return this.jejuShippingPrice;
    }

    public Product jejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
        return this;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return this.difficultShippingPrice;
    }

    public Product difficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
        return this;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return this.refundShippingPrice;
    }

    public Product refundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
        return this;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return this.exchangeShippingPrice;
    }

    public Product exchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
        return this;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public String getExchangeShippingFileUrl() {
        return this.exchangeShippingFileUrl;
    }

    public Product exchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
        return this;
    }

    public void setExchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
    }

    public Boolean getIsView() {
        return this.isView;
    }

    public Product isView(Boolean isView) {
        this.isView = isView;
        return this;
    }

    public void setIsView(Boolean isView) {
        this.isView = isView;
    }

    public Instant getViewReservationDate() {
        return this.viewReservationDate;
    }

    public Product viewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
        return this;
    }

    public void setViewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Product activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Product createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public Product createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public Product lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductCategoryRel> getProductCategoryRels() {
        return this.productCategoryRels;
    }

    public Product productCategoryRels(Set<ProductCategoryRel> productCategoryRels) {
        this.setProductCategoryRels(productCategoryRels);
        return this;
    }

    public void setProductCategoryRels(Set<ProductCategoryRel> productCategoryRels) {
        if (this.productCategoryRels != null) {
            this.productCategoryRels.forEach(i -> i.setProduct(null));
        }
        if (productCategoryRels != null) {
            productCategoryRels.forEach(i -> i.setProduct(this));
        }
        this.productCategoryRels = productCategoryRels;
    }

    public Set<ProductLabelRel> getProductLabelRels() {
        return this.productLabelRels;
    }

    public Product productLabelRels(Set<ProductLabelRel> productLabelRels) {
        this.setProductLabelRels(productLabelRels);
        return this;
    }

    public void setProductLabelRels(Set<ProductLabelRel> productLabelRels) {
        if (this.productLabelRels != null) {
            this.productLabelRels.forEach(i -> i.setProduct(null));
        }
        if (productLabelRels != null) {
            productLabelRels.forEach(i -> i.setProduct(this));
        }
        this.productLabelRels = productLabelRels;
    }

    public Set<ProductMappingRel> getProductMappingRels() {
        return this.productMappingRels;
    }

    public Product productMappingRels(Set<ProductMappingRel> productMappingRels) {
        this.setProductMappingRels(productMappingRels);
        return this;
    }

    public void setProductMappingRels(Set<ProductMappingRel> productMappingRels) {
        if (this.productMappingRels != null) {
            this.productMappingRels.forEach(i -> i.setProduct(null));
        }
        if (productMappingRels != null) {
            productMappingRels.forEach(i -> i.setProduct(this));
        }
        this.productMappingRels = productMappingRels;
    }

    public Set<ProductViewRel> getProductViewRels() {
        return this.productViewRels;
    }

    public Product productViewRels(Set<ProductViewRel> productViewRels) {
        this.setProductViewRels(productViewRels);
        return this;
    }

    public void setProductViewRels(Set<ProductViewRel> productViewRels) {
        if (this.productViewRels != null) {
            this.productViewRels.forEach(i -> i.setProduct(null));
        }
        if (productViewRels != null) {
            productViewRels.forEach(i -> i.setProduct(this));
        }
        this.productViewRels = productViewRels;
    }

    public Set<ProductNoticeRel> getProductNoticeRels() {
        return this.productNoticeRels;
    }

    public Product productNoticeRels(Set<ProductNoticeRel> productNoticeRels) {
        this.setProductNoticeRels(productNoticeRels);
        return this;
    }

    public void setProductNoticeRels(Set<ProductNoticeRel> productNoticeRels) {
        if (this.productNoticeRels != null) {
            this.productNoticeRels.forEach(i -> i.setProduct(null));
        }
        if (productNoticeRels != null) {
            productNoticeRels.forEach(i -> i.setProduct(this));
        }
        this.productNoticeRels = productNoticeRels;
    }

    public Set<ProductShippingRel> getProductShippingRels() {
        return this.productShippingRels;
    }

    public Product productShippingRels(Set<ProductShippingRel> productShippingRels) {
        this.setProductShippingRels(productShippingRels);
        return this;
    }

    public void setProductShippingRels(Set<ProductShippingRel> productShippingRels) {
        if (this.productShippingRels != null) {
            this.productShippingRels.forEach(i -> i.setProduct(null));
        }
        if (productShippingRels != null) {
            productShippingRels.forEach(i -> i.setProduct(this));
        }
        this.productShippingRels = productShippingRels;
    }

    public Set<ProductTemplateRel> getProductTemplateRels() {
        return this.productTemplateRels;
    }

    public Product productTemplateRels(Set<ProductTemplateRel> productTemplateRels) {
        this.setProductTemplateRels(productTemplateRels);
        return this;
    }

    public void setProductTemplateRels(Set<ProductTemplateRel> productTemplateRels) {
        if (this.productTemplateRels != null) {
            this.productTemplateRels.forEach(i -> i.setProduct(null));
        }
        if (productTemplateRels != null) {
            productTemplateRels.forEach(i -> i.setProduct(this));
        }
        this.productTemplateRels = productTemplateRels;
    }

    public Set<ProductOptionRel> getProductOptionRels() {
        return this.productOptionRels;
    }

    public Product productOptionRels(Set<ProductOptionRel> productOptionRels) {
        this.setProductOptionRels(productOptionRels);
        return this;
    }

    public void setProductOptionRels(Set<ProductOptionRel> productOptionRels) {
        if (this.productOptionRels != null) {
            this.productOptionRels.forEach(i -> i.setProduct(null));
        }
        if (productOptionRels != null) {
            productOptionRels.forEach(i -> i.setProduct(this));
        }
        this.productOptionRels = productOptionRels;
    }

    public Set<ProductClazzRel> getProductClazzRels() {
        return this.productClazzRels;
    }

    public Product productClazzRels(Set<ProductClazzRel> productClazzRels) {
        this.setProductClazzRels(productClazzRels);
        return this;
    }

    public void setProductClazzRels(Set<ProductClazzRel> productClazzRels) {
        if (this.productClazzRels != null) {
            this.productClazzRels.forEach(i -> i.setProduct(null));
        }
        if (productClazzRels != null) {
            productClazzRels.forEach(i -> i.setProduct(this));
        }
        this.productClazzRels = productClazzRels;
    }

    public Set<ProductStoreRel> getProductStoreRels() {
        return this.productStoreRels;
    }

    public Product productStoreRels(Set<ProductStoreRel> productStoreRels) {
        this.setProductStoreRels(productStoreRels);
        return this;
    }

    public void setProductStoreRels(Set<ProductStoreRel> productStoreRels) {
        if (this.productStoreRels != null) {
            this.productStoreRels.forEach(i -> i.setProduct(null));
        }
        if (productStoreRels != null) {
            productStoreRels.forEach(i -> i.setProduct(this));
        }
        this.productStoreRels = productStoreRels;
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
            ", code='" + getCode() + "'" +
            ", calculation='" + getCalculation() + "'" +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", price=" + getPrice() +
            ", allPriceUnit='" + getAllPriceUnit() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", discountPrice='" + getDiscountPrice() + "'" +
            ", discountUnit='" + getDiscountUnit() + "'" +
            ", discountDateFrom='" + getDiscountDateFrom() + "'" +
            ", discountDateTo='" + getDiscountDateTo() + "'" +
            ", isInstallment='" + getIsInstallment() + "'" +
            ", installmentMonth=" + getInstallmentMonth() +
            ", isSell='" + getIsSell() + "'" +
            ", sellDateFrom='" + getSellDateFrom() + "'" +
            ", sellDateTo='" + getSellDateTo() + "'" +
            ", minPurchaseAmount=" + getMinPurchaseAmount() +
            ", manPurchaseAmount=" + getManPurchaseAmount() +
            ", mainImageFileUrl='" + getMainImageFileUrl() + "'" +
            ", addImageFileUrl='" + getAddImageFileUrl() + "'" +
            ", mainVideoFileUrl='" + getMainVideoFileUrl() + "'" +
            ", descriptionFileUrl='" + getDescriptionFileUrl() + "'" +
            ", shippingType='" + getShippingType() + "'" +
            ", separateShippingPriceType='" + getSeparateShippingPriceType() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", freeShippingPrice=" + getFreeShippingPrice() +
            ", jejuShippingPrice=" + getJejuShippingPrice() +
            ", difficultShippingPrice=" + getDifficultShippingPrice() +
            ", refundShippingPrice=" + getRefundShippingPrice() +
            ", exchangeShippingPrice=" + getExchangeShippingPrice() +
            ", exchangeShippingFileUrl='" + getExchangeShippingFileUrl() + "'" +
            ", isView='" + getIsView() + "'" +
            ", viewReservationDate='" + getViewReservationDate() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
