package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductNoticeManage.
 */
@Entity
@Table(name = "product_notice_manage")
public class ProductNoticeManage extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "content_file_url")
    private String contentFileUrl;

    @Column(name = "priority_display")
    private Boolean priorityDisplay;

    @Column(name = "all_product_display")
    private Boolean allProductDisplay;

    @Column(name = "target")
    private String target;

    @Column(name = "enable_display_date")
    private Boolean enableDisplayDate;

    @Column(name = "display_date_from")
    private Instant displayDateFrom;

    @Column(name = "display_date_to")
    private Instant displayDateTo;

    @Column(name = "activated")
    private Boolean activated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentFileUrl() {
        return this.contentFileUrl;
    }

    public void setContentFileUrl(String contentFileUrl) {
        this.contentFileUrl = contentFileUrl;
    }

    public Boolean getPriorityDisplay() {
        return this.priorityDisplay;
    }

    public void setPriorityDisplay(Boolean priorityDisplay) {
        this.priorityDisplay = priorityDisplay;
    }

    public Boolean getAllProductDisplay() {
        return this.allProductDisplay;
    }

    public void setAllProductDisplay(Boolean allProductDisplay) {
        this.allProductDisplay = allProductDisplay;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getEnableDisplayDate() {
        return this.enableDisplayDate;
    }

    public void setEnableDisplayDate(Boolean enableDisplayDate) {
        this.enableDisplayDate = enableDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return this.displayDateFrom;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return this.displayDateTo;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductNoticeManage)) {
            return false;
        }
        return id != null && id.equals(((ProductNoticeManage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNoticeManage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
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
