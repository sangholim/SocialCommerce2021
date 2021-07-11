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

    @Column(name = "name")
    private String name;

    @Column(name = "thumb_file_url")
    private String thumbFileUrl;

    @Column(name = "original_link_url")
    private String originalLinkUrl;

    @Column(name = "time")
    private String time;

    @Column(name = "order")
    private Integer order;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clazzChapterVideos", "clazz" }, allowSetters = true)
    private ClazzChapter clazzChapter;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClazzChapterVideo id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ClazzChapterVideo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbFileUrl() {
        return this.thumbFileUrl;
    }

    public ClazzChapterVideo thumbFileUrl(String thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
        return this;
    }

    public void setThumbFileUrl(String thumbFileUrl) {
        this.thumbFileUrl = thumbFileUrl;
    }

    public String getOriginalLinkUrl() {
        return this.originalLinkUrl;
    }

    public ClazzChapterVideo originalLinkUrl(String originalLinkUrl) {
        this.originalLinkUrl = originalLinkUrl;
        return this;
    }

    public void setOriginalLinkUrl(String originalLinkUrl) {
        this.originalLinkUrl = originalLinkUrl;
    }

    public String getTime() {
        return this.time;
    }

    public ClazzChapterVideo time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getOrder() {
        return this.order;
    }

    public ClazzChapterVideo order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ClazzChapterVideo activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ClazzChapterVideo createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ClazzChapterVideo createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ClazzChapterVideo lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ClazzChapterVideo lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public ClazzChapter getClazzChapter() {
        return this.clazzChapter;
    }

    public ClazzChapterVideo clazzChapter(ClazzChapter clazzChapter) {
        this.setClazzChapter(clazzChapter);
        return this;
    }

    public void setClazzChapter(ClazzChapter clazzChapter) {
        this.clazzChapter = clazzChapter;
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
            ", name='" + getName() + "'" +
            ", thumbFileUrl='" + getThumbFileUrl() + "'" +
            ", originalLinkUrl='" + getOriginalLinkUrl() + "'" +
            ", time='" + getTime() + "'" +
            ", order=" + getOrder() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
