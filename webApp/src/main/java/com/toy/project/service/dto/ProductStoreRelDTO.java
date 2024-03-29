package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.ProductStoreRel} entity.
 */
public class ProductStoreRelDTO implements Serializable {

    private Long id;

    private Long productId;

    private Long storeId;

    private Boolean useCalculation;

    private String calculation;

    private Instant calculationDateFrom;

    private Instant calculationDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductDTO product;

    private StoreDTO store;

    public ProductStoreRelDTO() {}

    public ProductStoreRelDTO(
        Long id,
        Long productId,
        Long storeId,
        Boolean useCalculation,
        String calculation,
        Instant calculationDateFrom,
        Instant calculationDateTo,
        Boolean activated
    ) {
        this.id = id;
        this.productId = productId;
        this.storeId = storeId;
        this.useCalculation = useCalculation;
        this.calculation = calculation;
        this.calculationDateFrom = calculationDateFrom;
        this.calculationDateTo = calculationDateTo;
        this.activated = activated;
    }

    public static Set<ProductStoreRelDTO> toSet(Long productId, Boolean activated, Collection<StoreExtendDTO> storeDTOS) {
        if (CollectionUtils.isEmpty(storeDTOS)) {
            return null;
        }
        return storeDTOS
            .stream()
            .filter(Objects::nonNull)
            .map(
                storeDTO ->
                    new ProductStoreRelDTO(
                        null,
                        productId,
                        storeDTO.getId(),
                        storeDTO.getProductUseCalculation(),
                        storeDTO.getProductCalculation(),
                        storeDTO.getProductCalculationDateFrom(),
                        storeDTO.getProductCalculationDateTo(),
                        activated
                    )
            )
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Boolean getUseCalculation() {
        return useCalculation;
    }

    public void setUseCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return calculationDateFrom;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return calculationDateTo;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStoreRelDTO)) {
            return false;
        }

        ProductStoreRelDTO productStoreRelDTO = (ProductStoreRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productStoreRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreRelDTO{" +
            "id=" + getId() +
            ", useCalculation='" + getUseCalculation() + "'" +
            ", calculation=" + getCalculation() +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", product=" + getProduct() +
            ", store=" + getStore() +
            "}";
    }
}
