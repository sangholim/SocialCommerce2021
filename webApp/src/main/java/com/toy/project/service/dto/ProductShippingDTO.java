package com.toy.project.service.dto;

import com.toy.project.domain.ProductShipping;
import com.toy.project.domain.ProductTemplate;
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
 * A DTO for the {@link com.toy.project.domain.ProductShipping} entity.
 */
public class ProductShippingDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean isGroup;

    private Integer defaultShippingPrice;

    private Integer freeShippingPrice;

    private Integer jejuShippingPrice;

    private Integer difficultShippingPrice;

    private Integer refundShippingPrice;

    private Integer exchangeShippingPrice;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ProductShippingDTO() {}

    public ProductShippingDTO(ProductShipping productShipping) {
        if (productShipping == null) {
            return;
        }
        this.id = productShipping.getId();
        this.name = productShipping.getName();
        this.isGroup = productShipping.getIsGroup();
        this.defaultShippingPrice = productShipping.getDefaultShippingPrice();
        this.freeShippingPrice = productShipping.getFreeShippingPrice();
        this.jejuShippingPrice = productShipping.getJejuShippingPrice();
        this.difficultShippingPrice = productShipping.getDifficultShippingPrice();
        this.refundShippingPrice = productShipping.getRefundShippingPrice();
        this.exchangeShippingPrice = productShipping.getExchangeShippingPrice();
        this.activated = productShipping.getActivated();
        this.createdBy = productShipping.getCreatedBy();
        this.createdDate = productShipping.getCreatedDate();
        this.lastModifiedBy = productShipping.getLastModifiedBy();
        this.lastModifiedDate = productShipping.getLastModifiedDate();
    }

    public static Set<ProductShippingDTO> toSet(Collection<ProductShipping> productShippings) {
        if (CollectionUtils.isEmpty(productShippings)) {
            return null;
        }
        return productShippings.stream().map(ProductShippingDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductShippingDTO> toList(Collection<ProductShipping> productShippings) {
        if (CollectionUtils.isEmpty(productShippings)) {
            return null;
        }
        return productShippings.stream().map(ProductShippingDTO::new).collect(Collectors.toList());
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

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Integer getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return freeShippingPrice;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return jejuShippingPrice;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return difficultShippingPrice;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return refundShippingPrice;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return exchangeShippingPrice;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
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
        if (!(o instanceof ProductShippingDTO)) {
            return false;
        }

        ProductShippingDTO productShippingDTO = (ProductShippingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productShippingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductShippingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isGroup='" + getIsGroup() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", freeShippingPrice=" + getFreeShippingPrice() +
            ", jejuShippingPrice=" + getJejuShippingPrice() +
            ", difficultShippingPrice=" + getDifficultShippingPrice() +
            ", refundShippingPrice=" + getRefundShippingPrice() +
            ", exchangeShippingPrice=" + getExchangeShippingPrice() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
