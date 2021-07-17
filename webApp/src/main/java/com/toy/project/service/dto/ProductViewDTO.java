package com.toy.project.service.dto;

import com.toy.project.domain.ProductView;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductView} entity.
 */
public class ProductViewDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductViewDTO() {}

    public ProductViewDTO(ProductView productView) {
        if (productView == null) {
            return;
        }
        this.id = productView.getId();
        this.activated = productView.getActivated();
        this.createdBy = productView.getCreatedBy();
        this.createdDate = productView.getCreatedDate();
        this.lastModifiedBy = productView.getLastModifiedBy();
        this.lastModifiedDate = productView.getLastModifiedDate();
        this.name = productView.getName();
    }

    public static Set<ProductViewDTO> toSet(Collection<ProductView> productViews) {
        if (CollectionUtils.isEmpty(productViews)) {
            return null;
        }
        return productViews.stream().map(ProductViewDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductViewDTO> toList(Collection<ProductView> productViews) {
        if (CollectionUtils.isEmpty(productViews)) {
            return null;
        }
        return productViews.stream().map(ProductViewDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductViewDTO)) {
            return false;
        }

        ProductViewDTO productViewDTO = (ProductViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productViewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductViewDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
