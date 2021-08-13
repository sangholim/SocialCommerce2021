package com.toy.project.service.dto;

import com.toy.project.domain.ClazzChapterVideo;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ClazzChapter} entity.
 */
public class ClazzChapterDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String description;

    private String fileUrl;

    private Integer sequence;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<ClazzChapterVideo> clazzChapterVideos = new ArrayList<>();

    private Long clazzId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public List<ClazzChapterVideo> getClazzChapterVideos() {
        return clazzChapterVideos;
    }

    public void setClazzChapterVideos(List<ClazzChapterVideo> clazzChapterVideos) {
        this.clazzChapterVideos = clazzChapterVideos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClazzChapterDTO)) {
            return false;
        }

        ClazzChapterDTO clazzChapterDTO = (ClazzChapterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clazzChapterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "ClazzChapterDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", fileUrl='" +
            fileUrl +
            '\'' +
            ", sequence=" +
            sequence +
            ", activated=" +
            activated +
            ", createdBy='" +
            createdBy +
            '\'' +
            ", createdDate=" +
            createdDate +
            ", lastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", lastModifiedDate=" +
            lastModifiedDate +
            ", clazzChapterVideos=" +
            clazzChapterVideos +
            ", clazzId=" +
            clazzId +
            '}'
        );
    }
}
