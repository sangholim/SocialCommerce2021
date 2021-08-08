package com.toy.project.web.rest.vm;

import com.toy.project.service.dto.ProductAddImageDTO;
import org.springframework.web.multipart.MultipartFile;

public class ProductAddImageVM extends ProductAddImageDTO {

    private MultipartFile imageFile;

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "ProductAddImageVM{" + "imageFile=" + imageFile + '}';
    }
}
