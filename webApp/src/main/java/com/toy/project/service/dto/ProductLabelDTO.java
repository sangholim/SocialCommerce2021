package com.toy.project.service.dto;

import com.toy.project.domain.ProductLabel;
import com.toy.project.domain.Store;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductLabel} entity.
 */
public class ProductLabelDTO implements Serializable {

    private Long id;

    private String name;

    private String color;

    @Lob
    private String content;

    private String type;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductLabelDTO() {}

    public ProductLabelDTO(ProductLabel productLabel) {
        if (productLabel == null) {
            return;
        }
        this.id = productLabel.getId();
        this.name = productLabel.getName();
        this.color = productLabel.getColor();
        this.content = productLabel.getContent();
        this.type = productLabel.getType();
        this.activated = productLabel.getActivated();
        this.createdBy = productLabel.getCreatedBy();
        this.createdDate = productLabel.getCreatedDate();
        this.lastModifiedBy = productLabel.getLastModifiedBy();
        this.lastModifiedDate = productLabel.getLastModifiedDate();
    }

    public static Set<ProductLabelDTO> toSet(Collection<ProductLabel> productLabels) {
        if (CollectionUtils.isEmpty(productLabels)) {
            return null;
        }
        return productLabels.stream().map(ProductLabelDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductLabelDTO> toList(Collection<ProductLabel> productLabels) {
        if (CollectionUtils.isEmpty(productLabels)) {
            return null;
        }
        return productLabels.stream().map(ProductLabelDTO::new).collect(Collectors.toList());
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof ProductLabelDTO)) {
            return false;
        }

        ProductLabelDTO productLabelDTO = (ProductLabelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productLabelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", content='" + getContent() + "'" +
            ", type='" + getType() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
