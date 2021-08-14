package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PackageDescriptionImage.
 */
@Entity
@Table(name = "package_description_image")
public class PackageDescriptionImage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_description_id")
    private Long packageDescriptionId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "package_description_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "packageDescriptionImages", "product" }, allowSetters = true)
    private PackageDescription packageDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageDescriptionImage id(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public PackageDescriptionImage imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public PackageDescriptionImage activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public PackageDescription getPackageDescription() {
        return this.packageDescription;
    }

    public PackageDescriptionImage packageDescription(PackageDescription packageDescription) {
        this.setPackageDescription(packageDescription);
        return this;
    }

    public void setPackageDescription(PackageDescription packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Long getPackageDescriptionId() {
        return packageDescriptionId;
    }

    public void setPackageDescriptionId(Long packageDescriptionId) {
        this.packageDescriptionId = packageDescriptionId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageDescriptionImage)) {
            return false;
        }
        return id != null && id.equals(((PackageDescriptionImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "PackageDescriptionImage{" +
            "id=" +
            id +
            ", packageDescriptionId=" +
            packageDescriptionId +
            ", imageUrl='" +
            imageUrl +
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
            ", packageDescription=" +
            packageDescription +
            '}'
        );
    }
}
