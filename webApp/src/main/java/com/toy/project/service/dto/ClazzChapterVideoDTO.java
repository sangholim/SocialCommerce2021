package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ClazzChapterVideo} entity.
 */
public class ClazzChapterVideoDTO implements Serializable {

    private Long id;

    private String name;

    private String thumbFileUrl;

    private String originalLinkUrl;

    private String time;

    private Integer sequence;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long clazzChapterId;

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

    public String getThumbFileUrl() {
        return thumbFileUrl;
    }

    public void setThumbFileUrl(String thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
    }

    public String getOriginalLinkUrl() {
        return originalLinkUrl;
    }

    public void setOriginalLinkUrl(String originalLinkUrl) {
        this.originalLinkUrl = originalLinkUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Long getClazzChapterId() {
        return clazzChapterId;
    }

    public void setClazzChapterId(Long clazzChapterId) {
        this.clazzChapterId = clazzChapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClazzChapterVideoDTO)) {
            return false;
        }

        ClazzChapterVideoDTO clazzChapterVideoDTO = (ClazzChapterVideoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clazzChapterVideoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzChapterVideoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thumbFileUrl='" + getThumbFileUrl() + "'" +
            ", originalLinkUrl='" + getOriginalLinkUrl() + "'" +
            ", time='" + getTime() + "'" +
            ", sequence=" + getSequence() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", clazzChapterId=" + getClazzChapterId() +
            "}";
    }
}
