package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ClazzChapter.
 */
@Entity
@Table(name = "clazz_chapter")
public class ClazzChapter extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "order")
    private Integer order;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "clazzChapter")
    @JsonIgnoreProperties(value = { "clazzChapter" }, allowSetters = true)
    private Set<ClazzChapterVideo> clazzChapterVideos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "productClazzRels", "clazzChapters" }, allowSetters = true)
    private Clazz clazz;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClazzChapter id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ClazzChapter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ClazzChapter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public ClazzChapter fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getOrder() {
        return this.order;
    }

    public ClazzChapter order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ClazzChapter activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ClazzChapter createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ClazzChapter createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ClazzChapter lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ClazzChapter lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ClazzChapterVideo> getClazzChapterVideos() {
        return this.clazzChapterVideos;
    }

    public ClazzChapter clazzChapterVideos(Set<ClazzChapterVideo> clazzChapterVideos) {
        this.setClazzChapterVideos(clazzChapterVideos);
        return this;
    }

    public ClazzChapter addClazzChapterVideo(ClazzChapterVideo clazzChapterVideo) {
        this.clazzChapterVideos.add(clazzChapterVideo);
        clazzChapterVideo.setClazzChapter(this);
        return this;
    }

    public ClazzChapter removeClazzChapterVideo(ClazzChapterVideo clazzChapterVideo) {
        this.clazzChapterVideos.remove(clazzChapterVideo);
        clazzChapterVideo.setClazzChapter(null);
        return this;
    }

    public void setClazzChapterVideos(Set<ClazzChapterVideo> clazzChapterVideos) {
        if (this.clazzChapterVideos != null) {
            this.clazzChapterVideos.forEach(i -> i.setClazzChapter(null));
        }
        if (clazzChapterVideos != null) {
            clazzChapterVideos.forEach(i -> i.setClazzChapter(this));
        }
        this.clazzChapterVideos = clazzChapterVideos;
    }

    public Clazz getClazz() {
        return this.clazz;
    }

    public ClazzChapter clazz(Clazz clazz) {
        this.setClazz(clazz);
        return this;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClazzChapter)) {
            return false;
        }
        return id != null && id.equals(((ClazzChapter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzChapter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", order=" + getOrder() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
