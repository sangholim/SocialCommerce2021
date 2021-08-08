package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClazzChapterVideo.
 */
@Entity
@Table(name = "clazz_chapter_video")
public class ClazzChapterVideo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clazz_chapter_id")
    private Long clazzChapterId;

    @Column(name = "name")
    private String name;

    @Column(name = "thumb_file_url")
    private String thumbFileUrl;

    @Column(name = "original_link_url")
    private String originalLinkUrl;

    @Column(name = "time")
    private String time;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "clazz_chapter_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "clazzChapterVideos", "clazz" }, allowSetters = true)
    private ClazzChapter clazzChapter;

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

    public String getThumbFileUrl() {
        return this.thumbFileUrl;
    }

    public void setThumbFileUrl(String thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
    }

    public String getOriginalLinkUrl() {
        return this.originalLinkUrl;
    }

    public void setOriginalLinkUrl(String originalLinkUrl) {
        this.originalLinkUrl = originalLinkUrl;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ClazzChapter getClazzChapter() {
        return this.clazzChapter;
    }

    public void setClazzChapter(ClazzChapter clazzChapter) {
        this.clazzChapter = clazzChapter;
    }

    public Long getClazzChapterId() {
        return clazzChapterId;
    }

    public void setClazzChapterId(Long clazzChapterId) {
        this.clazzChapterId = clazzChapterId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClazzChapterVideo)) {
            return false;
        }
        return id != null && id.equals(((ClazzChapterVideo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzChapterVideo{" +
            "id=" + getId() +
            ", clazzChapterId='" + getClazzChapterId() + "'" +
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
            "}";
    }
}
