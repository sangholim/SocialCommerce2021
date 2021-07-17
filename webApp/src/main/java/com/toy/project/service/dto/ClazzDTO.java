package com.toy.project.service.dto;

import com.toy.project.domain.Clazz;
import com.toy.project.domain.ProductTemplate;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;
import org.springframework.util.CollectionUtils;

/**
 * A DTO for the {@link com.toy.project.domain.Clazz} entity.
 */
public class ClazzDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String mainImageFileUrl;

    private String level;

    private Boolean enableLecture;

    private Boolean freeLecture;

    private Long priceLecture;

    private String priceUnitLecture;

    private Instant lectureStartDateFrom;

    private Integer lectureInterval;

    private String lecturer;

    private String calculation;

    private Boolean isView;

    private Boolean isSell;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ClazzDTO() {}

    public ClazzDTO(Clazz clazz) {
        if (clazz == null) {
            return;
        }
        this.id = clazz.getId();
        this.name = clazz.getName();
        this.type = clazz.getType();
        this.mainImageFileUrl = clazz.getMainImageFileUrl();
        this.level = clazz.getLevel();
        this.enableLecture = clazz.getEnableLecture();
        this.freeLecture = clazz.getFreeLecture();
        this.priceLecture = clazz.getPriceLecture();
        this.priceUnitLecture = clazz.getPriceUnitLecture();
        this.lectureStartDateFrom = clazz.getLectureStartDateFrom();
        this.lectureInterval = clazz.getLectureInterval();
        this.lecturer = clazz.getLecturer();
        this.calculation = clazz.getCalculation();
        this.isView = clazz.getIsView();
        this.isSell = clazz.getIsSell();
        this.activated = clazz.getActivated();
        this.createdBy = clazz.getCreatedBy();
        this.createdDate = clazz.getCreatedDate();
        this.lastModifiedBy = clazz.getLastModifiedBy();
        this.lastModifiedDate = clazz.getLastModifiedDate();
    }

    public static Set<ClazzDTO> toSet(Collection<Clazz> clazzs) {
        if (CollectionUtils.isEmpty(clazzs)) {
            return null;
        }
        return clazzs.stream().map(ClazzDTO::new).collect(Collectors.toSet());
    }

    public static List<ClazzDTO> toList(Collection<Clazz> clazzs) {
        if (CollectionUtils.isEmpty(clazzs)) {
            return null;
        }
        return clazzs.stream().map(ClazzDTO::new).collect(Collectors.toList());
    }

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

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Boolean getIsView() {
        return isView;
    }

    public void setIsView(Boolean isView) {
        this.isView = isView;
    }

    public Boolean getIsSell() {
        return isSell;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
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
            ", type='" + getType() + "'" +
            ", mainImageFileUrl='" + getMainImageFileUrl() + "'" +
            ", level='" + getLevel() + "'" +
            ", enableLecture='" + getEnableLecture() + "'" +
            ", freeLecture='" + getFreeLecture() + "'" +
            ", priceLecture=" + getPriceLecture() +
            ", priceUnitLecture='" + getPriceUnitLecture() + "'" +
            ", lectureStartDateFrom='" + getLectureStartDateFrom() + "'" +
            ", lectureInterval=" + getLectureInterval() +
            ", lecturer='" + getLecturer() + "'" +
            ", calculation=" + getCalculation() +
            ", isView='" + getIsView() + "'" +
            ", isSell='" + getIsSell() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
