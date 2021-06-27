package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
public class Store extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "calculation")
    private Integer calculation;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "store")
    @JsonIgnoreProperties(value = { "product", "store" }, allowSetters = true)
    private Set<ProductStoreRel> productStoreRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public Store type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public Store calculation(Integer calculation) {
        this.calculation = calculation;
        return this;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Store activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Store createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public Store createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public Store lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public Store lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductStoreRel> getProductStoreRels() {
        return this.productStoreRels;
    }

    public Store productStoreRels(Set<ProductStoreRel> productStoreRels) {
        this.setProductStoreRels(productStoreRels);
        return this;
    }

    public void setProductStoreRels(Set<ProductStoreRel> productStoreRels) {
        if (this.productStoreRels != null) {
            this.productStoreRels.forEach(i -> i.setStore(null));
        }
        if (productStoreRels != null) {
            productStoreRels.forEach(i -> i.setStore(this));
        }
        this.productStoreRels = productStoreRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", calculation=" + getCalculation() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
