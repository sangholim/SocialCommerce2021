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

    @Column(name = "clazz_id")
    private Long clazzId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "clazzChapter")
    @JsonIgnoreProperties(value = { "clazzChapter" }, allowSetters = true)
    private Set<ClazzChapterVideo> clazzChapterVideos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "clazz_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productClazzAuthors", "clazzChapters", "author" }, allowSetters = true)
    private Clazz clazz;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public Set<ClazzChapterVideo> getClazzChapterVideos() {
        return this.clazzChapterVideos;
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

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
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
            ", clazzId='" + getClazzId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileUrl='" + getFileUrl() + "'" +
            ", sequence=" + getSequence() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
