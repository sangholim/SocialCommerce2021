package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toy.project.domain.embedded.EmbeddedShipment;
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
@Table(name = "product")
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_notice_manage_id")
    private Long productNoticeManageId;

    @Column(name = "name")
    private String name;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "code")
    private String code;

    @Column(name = "use_class_option")
    private Boolean useClassOption;

    @Column(name = "price")
    private Integer price;

    @Column(name = "use_discount_instant")
    private Boolean useDiscountInstant;

    @Column(name = "use_installment")
    private Boolean useInstallment;

    @Column(name = "installment_month")
    private Integer installmentMonth;

    @Column(name = "use_sell_date")
    private Boolean useSellDate;

    @Column(name = "sell_date_from")
    private Instant sellDateFrom;

    @Column(name = "sell_date_to")
    private Instant sellDateTo;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "use_product_option")
    private Boolean useProductOption;

    @Column(name = "use_product_input_option")
    private Boolean useProductInputOption;

    @Column(name = "use_product_add_option")
    private Boolean useProductAddOption;

    @Column(name = "min_purchase_amount")
    private Integer minPurchaseAmount;

    @Column(name = "max_purchase_amount_per_count")
    private Integer maxPurchaseAmountPerCount;

    @Column(name = "max_purchase_amount_per_one")
    private Integer maxPurchaseAmountPerOne;

    @Column(name = "main_image_file_url")
    private String mainImageFileUrl;

    @Column(name = "main_video_file_url")
    private String mainVideoFileUrl;

    @Column(name = "use_product_announce")
    private Boolean useProductAnnounce;

    @Column(name = "use_product_faq")
    private Boolean useProductFaq;

    @Column(name = "description_file_url")
    private String descriptionFileUrl;

    @Embedded
    private EmbeddedShipment embeddedShipment;

    @Column(name = "use_view")
    private Boolean useView;

    @Column(name = "use_view_reservation")
    private Boolean useViewReservation;

    @Column(name = "view_reservation_date")
    private Instant viewReservationDate;

    @Column(name = "use_product_notice")
    private Boolean useProductNotice;

    @Column(name = "use_product_illegal")
    private Boolean useProductIllegal;

    @Column(name = "use_product_recommend")
    private Boolean useProductRecommend;

    @Column(name = "use_product_mapping")
    private Boolean useProductMapping;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "product_clazz_author_id")
    private Long productClazzAuthorId;

    @Column(name = "product_store_id")
    private Long productStoreId;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductDiscount> productDiscounts = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "productMappingManage", "product" }, allowSetters = true)
    private Set<ProductMapping> productMappings = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductOption> productOptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductAddOption> productAddOptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductInputOption> productInputOptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductFaq> productFaqs = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductAnnounce> productAnnounces = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ProductAddImage> productAddImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productLabelManage" }, allowSetters = true)
    private Set<ProductLabel> productLabels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productTemplateManage" }, allowSetters = true)
    private Set<ProductTemplate> productTemplates = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productCategoryManage" }, allowSetters = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "product_notice_manage_id", updatable = false, insertable = false)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private ProductNoticeManage productNoticeManage;

    @ManyToOne
    @JoinColumn(name = "product_clazz_author_id", updatable = false, insertable = false)
    @JsonIgnoreProperties(value = { "products", "clazz", "author" }, allowSetters = true)
    private ProductClazzAuthor productClazzAuthor;

    @ManyToOne
    @JoinColumn(name = "product_store_id", updatable = false, insertable = false)
    @JsonIgnoreProperties(value = { "products", "vendor", "brand" }, allowSetters = true)
    private ProductStore productStore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getInstallmentMonth() {
        return this.installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public Instant getSellDateFrom() {
        return this.sellDateFrom;
    }

    public void setSellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public Instant getSellDateTo() {
        return this.sellDateTo;
    }

    public void setSellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinPurchaseAmount() {
        return this.minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getMaxPurchaseAmountPerCount() {
        return this.maxPurchaseAmountPerCount;
    }

    public void setMaxPurchaseAmountPerCount(Integer maxPurchaseAmountPerCount) {
        this.maxPurchaseAmountPerCount = maxPurchaseAmountPerCount;
    }

    public Integer getMaxPurchaseAmountPerOne() {
        return this.maxPurchaseAmountPerOne;
    }

    public void setMaxPurchaseAmountPerOne(Integer maxPurchaseAmountPerOne) {
        this.maxPurchaseAmountPerOne = maxPurchaseAmountPerOne;
    }

    public String getMainImageFileUrl() {
        return this.mainImageFileUrl;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getMainVideoFileUrl() {
        return this.mainVideoFileUrl;
    }

    public void setMainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
    }

    public String getDescriptionFileUrl() {
        return this.descriptionFileUrl;
    }

    public void setDescriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
    }

    public EmbeddedShipment getEmbeddedShipment() {
        return embeddedShipment;
    }

    public void setEmbeddedShipment(EmbeddedShipment embeddedShipment) {
        this.embeddedShipment = embeddedShipment;
    }

    public Instant getViewReservationDate() {
        return this.viewReservationDate;
    }

    public void setViewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
    }

    public Boolean getUseClassOption() {
        return useClassOption;
    }

    public void setUseClassOption(Boolean useClassOption) {
        this.useClassOption = useClassOption;
    }

    public Boolean getUseDiscountInstant() {
        return useDiscountInstant;
    }

    public void setUseDiscountInstant(Boolean useDiscountInstant) {
        this.useDiscountInstant = useDiscountInstant;
    }

    public Boolean getUseInstallment() {
        return useInstallment;
    }

    public void setUseInstallment(Boolean useInstallment) {
        this.useInstallment = useInstallment;
    }

    public Boolean getUseSellDate() {
        return useSellDate;
    }

    public void setUseSellDate(Boolean useSellDate) {
        this.useSellDate = useSellDate;
    }

    public Boolean getUseProductOption() {
        return useProductOption;
    }

    public void setUseProductOption(Boolean useProductOption) {
        this.useProductOption = useProductOption;
    }

    public Boolean getUseProductInputOption() {
        return useProductInputOption;
    }

    public void setUseProductInputOption(Boolean useProductInputOption) {
        this.useProductInputOption = useProductInputOption;
    }

    public Boolean getUseProductAddOption() {
        return useProductAddOption;
    }

    public void setUseProductAddOption(Boolean useProductAddOption) {
        this.useProductAddOption = useProductAddOption;
    }

    public Boolean getUseProductAnnounce() {
        return useProductAnnounce;
    }

    public void setUseProductAnnounce(Boolean useProductAnnounce) {
        this.useProductAnnounce = useProductAnnounce;
    }

    public Boolean getUseProductFaq() {
        return useProductFaq;
    }

    public void setUseProductFaq(Boolean useProductFaq) {
        this.useProductFaq = useProductFaq;
    }

    public Boolean getUseView() {
        return useView;
    }

    public void setUseView(Boolean useView) {
        this.useView = useView;
    }

    public Boolean getUseViewReservation() {
        return useViewReservation;
    }

    public void setUseViewReservation(Boolean useViewReservation) {
        this.useViewReservation = useViewReservation;
    }

    public Boolean getUseProductNotice() {
        return useProductNotice;
    }

    public void setUseProductNotice(Boolean useProductNotice) {
        this.useProductNotice = useProductNotice;
    }

    public Boolean getUseProductIllegal() {
        return useProductIllegal;
    }

    public void setUseProductIllegal(Boolean useProductIllegal) {
        this.useProductIllegal = useProductIllegal;
    }

    public Boolean getUseProductRecommend() {
        return useProductRecommend;
    }

    public void setUseProductRecommend(Boolean useProductRecommend) {
        this.useProductRecommend = useProductRecommend;
    }

    public Boolean getUseProductMapping() {
        return useProductMapping;
    }

    public void setUseProductMapping(Boolean useProductMapping) {
        this.useProductMapping = useProductMapping;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<ProductDiscount> getProductDiscounts() {
        return this.productDiscounts;
    }

    public void setProductDiscounts(Set<ProductDiscount> productDiscounts) {
        if (this.productDiscounts != null) {
            this.productDiscounts.forEach(i -> i.setProduct(null));
        }
        if (productDiscounts != null) {
            productDiscounts.forEach(i -> i.setProduct(this));
        }
        this.productDiscounts = productDiscounts;
    }

    public Set<ProductMapping> getProductMappings() {
        return this.productMappings;
    }

    public void setProductMappings(Set<ProductMapping> productMappings) {
        if (this.productMappings != null) {
            this.productMappings.forEach(i -> i.setProduct(null));
        }
        if (productMappings != null) {
            productMappings.forEach(i -> i.setProduct(this));
        }
        this.productMappings = productMappings;
    }

    public Set<ProductOption> getProductOptions() {
        return this.productOptions;
    }

    public void setProductOptions(Set<ProductOption> productOptions) {
        if (this.productOptions != null) {
            this.productOptions.forEach(i -> i.setProduct(null));
        }
        if (productOptions != null) {
            productOptions.forEach(i -> i.setProduct(this));
        }
        this.productOptions = productOptions;
    }

    public Set<ProductAddOption> getProductAddOptions() {
        return this.productAddOptions;
    }

    public void setProductAddOptions(Set<ProductAddOption> productAddOptions) {
        if (this.productAddOptions != null) {
            this.productAddOptions.forEach(i -> i.setProduct(null));
        }
        if (productAddOptions != null) {
            productAddOptions.forEach(i -> i.setProduct(this));
        }
        this.productAddOptions = productAddOptions;
    }

    public Set<ProductInputOption> getProductInputOptions() {
        return this.productInputOptions;
    }

    public void setProductInputOptions(Set<ProductInputOption> productInputOptions) {
        if (this.productInputOptions != null) {
            this.productInputOptions.forEach(i -> i.setProduct(null));
        }
        if (productInputOptions != null) {
            productInputOptions.forEach(i -> i.setProduct(this));
        }
        this.productInputOptions = productInputOptions;
    }

    public Set<ProductFaq> getProductFaqs() {
        return this.productFaqs;
    }

    public void setProductFaqs(Set<ProductFaq> productFaqs) {
        if (this.productFaqs != null) {
            this.productFaqs.forEach(i -> i.setProduct(null));
        }
        if (productFaqs != null) {
            productFaqs.forEach(i -> i.setProduct(this));
        }
        this.productFaqs = productFaqs;
    }

    public Set<ProductAnnounce> getProductAnnounces() {
        return this.productAnnounces;
    }

    public void setProductAnnounces(Set<ProductAnnounce> productAnnounces) {
        if (this.productAnnounces != null) {
            this.productAnnounces.forEach(i -> i.setProduct(null));
        }
        if (productAnnounces != null) {
            productAnnounces.forEach(i -> i.setProduct(this));
        }
        this.productAnnounces = productAnnounces;
    }

    public Set<ProductAddImage> getProductAddImages() {
        return this.productAddImages;
    }

    public void setProductAddImages(Set<ProductAddImage> productAddImages) {
        if (this.productAddImages != null) {
            this.productAddImages.forEach(i -> i.setProduct(null));
        }
        if (productAddImages != null) {
            productAddImages.forEach(i -> i.setProduct(this));
        }
        this.productAddImages = productAddImages;
    }

    public Set<ProductLabel> getProductLabels() {
        return this.productLabels;
    }

    public void setProductLabels(Set<ProductLabel> productLabels) {
        if (this.productLabels != null) {
            this.productLabels.forEach(i -> i.setProduct(null));
        }
        if (productLabels != null) {
            productLabels.forEach(i -> i.setProduct(this));
        }
        this.productLabels = productLabels;
    }

    public Set<ProductTemplate> getProductTemplates() {
        return this.productTemplates;
    }

    public void setProductTemplates(Set<ProductTemplate> productTemplates) {
        if (this.productTemplates != null) {
            this.productTemplates.forEach(i -> i.setProduct(null));
        }
        if (productTemplates != null) {
            productTemplates.forEach(i -> i.setProduct(this));
        }
        this.productTemplates = productTemplates;
    }

    public Set<ProductCategory> getProductCategories() {
        return this.productCategories;
    }

    public void setProductCategories(Set<ProductCategory> productCategories) {
        if (this.productCategories != null) {
            this.productCategories.forEach(i -> i.setProduct(null));
        }
        if (productCategories != null) {
            productCategories.forEach(i -> i.setProduct(this));
        }
        this.productCategories = productCategories;
    }

    public ProductNoticeManage getProductNoticeManage() {
        return this.productNoticeManage;
    }

    public void setProductNoticeManage(ProductNoticeManage productNoticeManage) {
        this.productNoticeManage = productNoticeManage;
    }

    public ProductClazzAuthor getProductClazzAuthor() {
        return this.productClazzAuthor;
    }

    public void setProductClazzAuthor(ProductClazzAuthor productClazzAuthor) {
        this.productClazzAuthor = productClazzAuthor;
    }

    public ProductStore getProductStore() {
        return this.productStore;
    }

    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }

    public Long getProductNoticeManageId() {
        return productNoticeManageId;
    }

    public void setProductNoticeManageId(Long productNoticeManageId) {
        this.productNoticeManageId = productNoticeManageId;
    }

    public Long getProductClazzAuthorId() {
        return productClazzAuthorId;
    }

    public void setProductClazzAuthorId(Long productClazzAuthorId) {
        this.productClazzAuthorId = productClazzAuthorId;
    }

    public Long getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(Long productStoreId) {
        this.productStoreId = productStoreId;
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
            "id=" + id +
            ", productNoticeManageId=" + productNoticeManageId +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", status='" + status + '\'' +
            ", code='" + code + '\'' +
            ", useClassOption=" + useClassOption +
            ", price=" + price +
            ", useDiscountInstant=" + useDiscountInstant +
            ", useInstallment=" + useInstallment +
            ", installmentMonth=" + installmentMonth +
            ", useSellDate=" + useSellDate +
            ", sellDateFrom=" + sellDateFrom +
            ", sellDateTo=" + sellDateTo +
            ", quantity=" + quantity +
            ", useProductOption=" + useProductOption +
            ", useProductInputOption=" + useProductInputOption +
            ", useProductAddOption=" + useProductAddOption +
            ", minPurchaseAmount=" + minPurchaseAmount +
            ", maxPurchaseAmountPerCount=" + maxPurchaseAmountPerCount +
            ", maxPurchaseAmountPerOne=" + maxPurchaseAmountPerOne +
            ", mainImageFileUrl='" + mainImageFileUrl + '\'' +
            ", mainVideoFileUrl='" + mainVideoFileUrl + '\'' +
            ", useProductAnnounce=" + useProductAnnounce +
            ", useProductFaq=" + useProductFaq +
            ", descriptionFileUrl='" + descriptionFileUrl + '\'' +
            ", embeddedShipment='" + embeddedShipment + '\'' +
            ", useView=" + useView +
            ", useViewReservation=" + useViewReservation +
            ", viewReservationDate=" + viewReservationDate +
            ", useProductNotice=" + useProductNotice +
            ", useProductIllegal=" + useProductIllegal +
            ", useProductRecommend=" + useProductRecommend +
            ", useProductMapping=" + useProductMapping +
            ", activated=" + activated +
            ", createdBy='" + super.getCreatedBy() + '\'' +
            ", createdDate=" + super.getCreatedDate() +
            ", lastModifiedBy='" + super.getLastModifiedBy() + '\'' +
            ", lastModifiedDate=" + super.getLastModifiedDate() +
            ", productClazzAuthorId=" + productClazzAuthorId +
            ", productStoreId=" + productStoreId +
            ", productDiscounts=" + productDiscounts +
            ", productMappings=" + productMappings +
            ", productOptions=" + productOptions +
            ", productAddOptions=" + productAddOptions +
            ", productInputOptions=" + productInputOptions +
            ", productFaqs=" + productFaqs +
            ", productAnnounces=" + productAnnounces +
            ", productAddImages=" + productAddImages +
            ", productLabels=" + productLabels +
            ", productTemplates=" + productTemplates +
            ", productCategories=" + productCategories +
            ", productNoticeManage=" + productNoticeManage +
            ", productClazzAuthor=" + productClazzAuthor +
            ", productStore=" + productStore +
            '}';
    }
}
