package com.toy.project.service.dto;

import com.toy.project.domain.Store;
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
 * A DTO for the {@link com.toy.project.domain.Store} entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String calculation;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public StoreDTO() {}

    public StoreDTO(Store store) {
        if (store == null) {
            return;
        }
        this.id = store.getId();
        this.name = store.getName();
        this.type = store.getType();
        this.calculation = store.getCalculation();
        this.activated = store.getActivated();
        this.createdBy = store.getCreatedBy();
        this.createdDate = store.getCreatedDate();
        this.lastModifiedBy = store.getLastModifiedBy();
        this.lastModifiedDate = store.getLastModifiedDate();
    }

    public static Set<StoreDTO> toSet(Collection<Store> stores) {
        if (CollectionUtils.isEmpty(stores)) {
            return null;
        }
        return stores.stream().map(StoreDTO::new).collect(Collectors.toSet());
    }

    public static List<StoreDTO> toList(Collection<Store> stores) {
        if (CollectionUtils.isEmpty(stores)) {
            return null;
        }
        return stores.stream().map(StoreDTO::new).collect(Collectors.toList());
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
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
        if (!(o instanceof StoreDTO)) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StoreDTO{" +
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
