package com.toy.project.service.dto;

import com.toy.project.domain.ClazzChapter;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.Clazz} entity.
 */
public class ClazzDTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 50)
    private String status;

    private String type;

    private String mainImageFileUrl;

    private String level;

    private Boolean enableLecture;

    private Boolean freeLecture;

    private Long priceLecture;

    private String priceUnitLecture;

    private Instant lectureStartDateFrom;

    private Integer lectureInterval;

    private Integer calculation;

    @Size(max = 50)
    private String calculationUnit;

    private Boolean useView;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<ClazzChapter> clazzChapters = new ArrayList<>();

    private Long authorId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainImageFileUrl() {
        return mainImageFileUrl;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getEnableLecture() {
        return enableLecture;
    }

    public void setEnableLecture(Boolean enableLecture) {
        this.enableLecture = enableLecture;
    }

    public Boolean getFreeLecture() {
        return freeLecture;
    }

    public void setFreeLecture(Boolean freeLecture) {
        this.freeLecture = freeLecture;
    }

    public Long getPriceLecture() {
        return priceLecture;
    }

    public void setPriceLecture(Long priceLecture) {
        this.priceLecture = priceLecture;
    }

    public String getPriceUnitLecture() {
        return priceUnitLecture;
    }

    public void setPriceUnitLecture(String priceUnitLecture) {
        this.priceUnitLecture = priceUnitLecture;
    }

    public Instant getLectureStartDateFrom() {
        return lectureStartDateFrom;
    }

    public void setLectureStartDateFrom(Instant lectureStartDateFrom) {
        this.lectureStartDateFrom = lectureStartDateFrom;
    }

    public Integer getLectureInterval() {
        return lectureInterval;
    }

    public void setLectureInterval(Integer lectureInterval) {
        this.lectureInterval = lectureInterval;
    }

    public Integer getCalculation() {
        return calculation;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public String getCalculationUnit() {
        return calculationUnit;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<ClazzChapter> getClazzChapters() {
        return clazzChapters;
    }

    public void setClazzChapters(List<ClazzChapter> clazzChapters) {
        this.clazzChapters = clazzChapters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClazzDTO)) {
            return false;
        }

        ClazzDTO clazzDTO = (ClazzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clazzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClazzDTO{" +
            "id=" + getId() +
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
            ", authorId=" + getAuthorId() +
            "}";
    }
}
