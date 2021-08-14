package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PackageDescription.
 */
@Entity
@Table(name = "package_description")
public class PackageDescription extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "packageDescription")
    @JsonIgnoreProperties(value = { "packageDescription" }, allowSetters = true)
    private Set<PackageDescriptionImage> packageDescriptionImages = new HashSet<>();

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
            "packageDescriptions",
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

    public PackageDescription id(Long id) {
        this.id = id;
        return this;
    }

    public String getSubject() {
        return this.subject;
    }

    public PackageDescription subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public PackageDescription content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public PackageDescription activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<PackageDescriptionImage> getPackageDescriptionImages() {
        return this.packageDescriptionImages;
    }

    public PackageDescription packageDescriptionImages(Set<PackageDescriptionImage> packageDescriptionImages) {
        this.setPackageDescriptionImages(packageDescriptionImages);
        return this;
    }

    public PackageDescription addPackageDescriptionImage(PackageDescriptionImage packageDescriptionImage) {
        this.packageDescriptionImages.add(packageDescriptionImage);
        packageDescriptionImage.setPackageDescription(this);
        return this;
    }

    public PackageDescription removePackageDescriptionImage(PackageDescriptionImage packageDescriptionImage) {
        this.packageDescriptionImages.remove(packageDescriptionImage);
        packageDescriptionImage.setPackageDescription(null);
        return this;
    }

    public void setPackageDescriptionImages(Set<PackageDescriptionImage> packageDescriptionImages) {
        if (this.packageDescriptionImages != null) {
            this.packageDescriptionImages.forEach(i -> i.setPackageDescription(null));
        }
        if (packageDescriptionImages != null) {
            packageDescriptionImages.forEach(i -> i.setPackageDescription(this));
        }
        this.packageDescriptionImages = packageDescriptionImages;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return this.product;
    }

    public PackageDescription product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageDescription)) {
            return false;
        }
        return id != null && id.equals(((PackageDescription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "PackageDescription{" +
            "id=" +
            id +
            ", productId=" +
            productId +
            ", subject='" +
            subject +
            '\'' +
            ", content='" +
            content +
            '\'' +
            ", activated=" +
            activated +
            ", createdBy='" +
            getCreatedBy() +
            '\'' +
            ", createdDate=" +
            getCreatedDate() +
            ", lastModifiedBy='" +
            getLastModifiedBy() +
            '\'' +
            ", lastModifiedDate=" +
            getLastModifiedDate() +
            ", packageDescriptionImages=" +
            packageDescriptionImages +
            ", product=" +
            product +
            '}'
        );
    }
}
