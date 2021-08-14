package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.toy.project.domain.PackageDescription} entity.
 */
public class PackageDescriptionDTO implements Serializable {

    private Long id;

    private Long productId;

    private String subject;

    private String content;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<String> imageUrls = new ArrayList<>();

    private List<MultipartFile> imageFileList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<MultipartFile> getImageFileList() {
        return imageFileList;
    }

    public void setImageFileList(List<MultipartFile> imageFileList) {
        this.imageFileList = imageFileList;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageDescriptionDTO)) {
            return false;
        }

        PackageDescriptionDTO packageDescriptionDTO = (PackageDescriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, packageDescriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "PackageDescriptionDTO{" +
            "id=" +
            id +
            ", productId=" +
            productId +
            ", subject='" +
            subject +
            '\'' +
            ", content='" +
            content +
            '\'' +
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
            ", imageUrls=" +
            imageUrls +
            ", imageFileList=" +
            imageFileList +
            '}'
        );
    }
}
