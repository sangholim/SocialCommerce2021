package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductNotice} entity.
 */
public class ProductNoticeDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String contentFileUrl;

    private Boolean priorityDisplay;

    private Boolean allProductDisplay;

    private String target;

    private Boolean enableDisplayDate;

    private Instant displayDateFrom;

    private Instant displayDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

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

    public String getContentFileUrl() {
        return contentFileUrl;
    }

    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }

    public Boolean getPriorityDisplay() {
        return priorityDisplay;
    }

    public void setPriorityDisplay(Boolean priorityDisplay) {
        this.priorityDisplay = priorityDisplay;
    }

    public Boolean getAllProductDisplay() {
        return allProductDisplay;
    }

    public void setAllProductDisplay(Boolean allProductDisplay) {
        this.allProductDisplay = allProductDisplay;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getEnableDisplayDate() {
        return enableDisplayDate;
    }

    public void setEnableDisplayDate(Boolean enableDisplayDate) {
        this.enableDisplayDate = enableDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return displayDateFrom;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return displayDateTo;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
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
        if (!(o instanceof ProductNoticeDTO)) {
            return false;
        }

        ProductNoticeDTO productNoticeDTO = (ProductNoticeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productNoticeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNoticeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", contentFileUrl='" + getContentFileUrl() + "'" +
            ", priorityDisplay='" + getPriorityDisplay() + "'" +
            ", allProductDisplay='" + getAllProductDisplay() + "'" +
            ", target='" + getTarget() + "'" +
            ", enableDisplayDate='" + getEnableDisplayDate() + "'" +
            ", displayDateFrom='" + getDisplayDateFrom() + "'" +
            ", displayDateTo='" + getDisplayDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
