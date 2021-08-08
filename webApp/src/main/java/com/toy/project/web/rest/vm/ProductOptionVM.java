package com.toy.project.web.rest.vm;

import com.toy.project.service.dto.ProductOptionDTO;
import org.springframework.web.multipart.MultipartFile;

public class ProductOptionVM extends ProductOptionDTO {

    private MultipartFile packageThumbnail;

    @Override
    public MultipartFile getPackageThumbnail() {
        return packageThumbnail;
    }

    @Override
    public void setPackageThumbnail(MultipartFile packageThumbnail) {
        this.packageThumbnail = packageThumbnail;
    }

    @Override
    public String toString() {
        return "ProductOptionVM{" + "packageThumbnail=" + packageThumbnail + '}';
    }
}
