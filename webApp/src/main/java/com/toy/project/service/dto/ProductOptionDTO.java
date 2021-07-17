package com.toy.project.service.dto;

import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductView;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductOption} entity.
 */
public class ProductOptionDTO implements Serializable {

    private Long id;

    private String priceSign;

    private Integer price;

    private Integer stock;

    private String status;

    private String code;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductOptionDTO() {}

    public ProductOptionDTO(ProductOption productOption) {
        if (productOption == null) {
            return;
        }
        this.id = productOption.getId();
        this.priceSign = productOption.getPriceSign();
        this.price = productOption.getPrice();
        this.stock = productOption.getStock();
        this.status = productOption.getStatus();
        this.code = productOption.getCode();
        this.activated = productOption.getActivated();
        this.createdBy = productOption.getCreatedBy();
        this.createdDate = productOption.getCreatedDate();
        this.lastModifiedBy = productOption.getLastModifiedBy();
        this.lastModifiedDate = productOption.getLastModifiedDate();
    }

    public static Set<ProductOptionDTO> toSet(Collection<ProductOption> productOptions) {
        if (CollectionUtils.isEmpty(productOptions)) {
            return null;
        }
        return productOptions.stream().map(ProductOptionDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductOptionDTO> toList(Collection<ProductOption> productOptions) {
        if (CollectionUtils.isEmpty(productOptions)) {
            return null;
        }
        return productOptions.stream().map(ProductOptionDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceSign() {
        return priceSign;
    }

    public void setPriceSign(String priceSign) {
        this.priceSign = priceSign;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(o instanceof ProductOptionDTO)) {
            return false;
        }

        ProductOptionDTO productOptionDTO = (ProductOptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productOptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOptionDTO{" +
            "id=" + getId() +
            ", priceSign='" + getPriceSign() + "'" +
            ", price=" + getPrice() +
            ", stock=" + getStock() +
            ", status='" + getStatus() + "'" +
            ", code='" + getCode() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
