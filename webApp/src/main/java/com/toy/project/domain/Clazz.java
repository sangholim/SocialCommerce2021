package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import liquibase.pro.packaged.J;

/**
 * A Clazz.
 */
@Entity
@Table(name = "clazz")
public class Clazz extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "name")
    private String name;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "main_image_file_url")
    private String mainImageFileUrl;

    @Column(name = "level")
    private String level;

    @Column(name = "enable_lecture")
    private Boolean enableLecture;

    @Column(name = "free_lecture")
    private Boolean freeLecture;

    @Column(name = "price_lecture")
    private Long priceLecture;

    @Column(name = "price_unit_lecture")
    private String priceUnitLecture;

    @Column(name = "lecture_start_date_from")
    private Instant lectureStartDateFrom;

    @Column(name = "lecture_interval")
    private Integer lectureInterval;

    @Column(name = "calculation")
    private Integer calculation;

    @Size(max = 50)
    @Column(name = "calculation_unit", length = 50)
    private String calculationUnit;

    @Column(name = "use_view")
    private Boolean useView;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "clazz")
    @JsonIgnoreProperties(value = { "products", "clazz", "author" }, allowSetters = true)
    private Set<ProductClazzAuthor> productClazzAuthors = new HashSet<>();

    @OneToMany(mappedBy = "clazz")
    @JsonIgnoreProperties(value = { "clazzChapterVideos", "clazz" }, allowSetters = true)
    private Set<ClazzChapter> clazzChapters = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productClazzAuthors", "clazzes" }, allowSetters = true)
    private Author author;

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

    public String getMainImageFileUrl() {
        return this.mainImageFileUrl;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getEnableLecture() {
        return this.enableLecture;
    }

    public void setEnableLecture(Boolean enableLecture) {
        this.enableLecture = enableLecture;
    }

    public Boolean getFreeLecture() {
        return this.freeLecture;
    }

    public void setFreeLecture(Boolean freeLecture) {
        this.freeLecture = freeLecture;
    }

    public Long getPriceLecture() {
        return this.priceLecture;
    }

    public void setPriceLecture(Long priceLecture) {
        this.priceLecture = priceLecture;
    }

    public String getPriceUnitLecture() {
        return this.priceUnitLecture;
    }

    public void setPriceUnitLecture(String priceUnitLecture) {
        this.priceUnitLecture = priceUnitLecture;
    }

    public Instant getLectureStartDateFrom() {
        return this.lectureStartDateFrom;
    }

    public void setLectureStartDateFrom(Instant lectureStartDateFrom) {
        this.lectureStartDateFrom = lectureStartDateFrom;
    }

    public Integer getLectureInterval() {
        return this.lectureInterval;
    }

    public void setLectureInterval(Integer lectureInterval) {
        this.lectureInterval = lectureInterval;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public String getCalculationUnit() {
        return this.calculationUnit;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public Boolean getUseView() {
        return useView;
    }

    public void setUseView(Boolean useView) {
        this.useView = useView;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<ProductClazzAuthor> getProductClazzAuthors() {
        return this.productClazzAuthors;
    }

    public void setProductClazzAuthors(Set<ProductClazzAuthor> productClazzAuthors) {
        if (this.productClazzAuthors != null) {
            this.productClazzAuthors.forEach(i -> i.setClazz(null));
        }
        if (productClazzAuthors != null) {
            productClazzAuthors.forEach(i -> i.setClazz(this));
        }
        this.productClazzAuthors = productClazzAuthors;
    }

    public Set<ClazzChapter> getClazzChapters() {
        return this.clazzChapters;
    }

    public void setClazzChapters(Set<ClazzChapter> clazzChapters) {
        if (this.clazzChapters != null) {
            this.clazzChapters.forEach(i -> i.setClazz(null));
        }
        if (clazzChapters != null) {
            clazzChapters.forEach(i -> i.setClazz(this));
        }
        this.clazzChapters = clazzChapters;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clazz)) {
            return false;
        }
        return id != null && id.equals(((Clazz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clazz{" +
            "id=" + getId() +
            ", authorId='" + getAuthorId() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", mainImageFileUrl='" + getMainImageFileUrl() + "'" +
            ", level='" + getLevel() + "'" +
            ", enableLecture='" + getEnableLecture() + "'" +
            ", freeLecture='" + getFreeLecture() + "'" +
            ", priceLecture=" + getPriceLecture() +
            ", priceUnitLecture='" + getPriceUnitLecture() + "'" +
            ", lectureStartDateFrom='" + getLectureStartDateFrom() + "'" +
            ", lectureInterval=" + getLectureInterval() +
            ", calculation=" + getCalculation() +
            ", calculationUnit='" + getCalculationUnit() + "'" +
            ", useView='" + getUseView() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
