package com.toy.project.web.rest.vm;

import com.toy.project.service.dto.*;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

public class ProductVM extends ProductDTO {

    @ApiModelProperty(value = "상품 메인 이미지 파일")
    private MultipartFile mainImageFile;

    @ApiModelProperty(value = "상품 메인 비디오 파일")
    private MultipartFile mainVideoFile;

    @ApiModelProperty(value = "상품 메인 상세 설명 파일")
    private MultipartFile descriptionFile;

    @ApiModelProperty(value = "상품 배송 환불 교환 안내 파일")
    private MultipartFile exchangeShippingFile;

    @ApiModelProperty(value = "상품 선택 옵션 리스트")
    private List<ProductOptionVM> productOptionList;

    @ApiModelProperty(value = "상품 카테고리 리스트")
    private List<ProductCategoryDTO> productCategoryList;

    @ApiModelProperty(value = "상품 추가 이미지 리스트")
    private List<ProductAddImageVM> productAddImageList;

    @ApiModelProperty(value = "상품 즉시 할인")
    private List<ProductDiscountDTO> productDiscountList;

    @ApiModelProperty(value = "상품 직접 입력 옵션")
    private List<ProductInputOptionDTO> productInputOptionList;

    @ApiModelProperty(value = "상품 추가 옵션")
    private List<ProductAddOptionDTO> productAddOptionList;

    @ApiModelProperty(value = "상품 고시")
    private List<ProductAnnounceDTO> productAnnounceList;

    @ApiModelProperty(value = "상품 자주 묻는 질문")
    private List<ProductFaqDTO> productFaqList;

    @ApiModelProperty(value = "상품 템플릿")
    private List<ProductTemplateDTO> productTemplateList;

    @ApiModelProperty(value = "추천, 연관 상품 관리")
    private List<ProductMappingDTO> productMappingList;

    @ApiModelProperty(value = "상품 라벨")
    private List<ProductLabelDTO> productLabelList;

    public ProductDTO toProductDto() {
        if (!CollectionUtils.isEmpty(productOptionList)) {
            super.setProductOptions(productOptionList.stream().map(ProductOptionDTO.class::cast).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(productAddImageList)) {
            super.setProductAddImages(productAddImageList.stream().map(ProductAddImageDTO.class::cast).collect(Collectors.toList()));
        }
        super.setProductCategories(productCategoryList);
        super.setProductDiscounts(productDiscountList);
        super.setProductInputOptions(productInputOptionList);
        super.setProductAddOptions(productAddOptionList);
        super.setProductAnnounces(productAnnounceList);
        super.setProductFaqs(productFaqList);
        super.setProductTemplates(productTemplateList);
        super.setProductMappings(productMappingList);
        super.setProductLabels(productLabelList);

        return this;
    }

    public MultipartFile getMainImageFile() {
        return mainImageFile;
    }

    public void setMainImageFile(MultipartFile mainImageFile) {
        this.mainImageFile = mainImageFile;
    }

    public MultipartFile getMainVideoFile() {
        return mainVideoFile;
    }

    public void setMainVideoFile(MultipartFile mainVideoFile) {
        this.mainVideoFile = mainVideoFile;
    }

    public MultipartFile getDescriptionFile() {
        return descriptionFile;
    }

    public void setDescriptionFile(MultipartFile descriptionFile) {
        this.descriptionFile = descriptionFile;
    }

    public MultipartFile getExchangeShippingFile() {
        return exchangeShippingFile;
    }

    public void setExchangeShippingFile(MultipartFile exchangeShippingFile) {
        this.exchangeShippingFile = exchangeShippingFile;
    }

    public List<ProductOptionVM> getProductOptionList() {
        return productOptionList;
    }

    public void setProductOptionList(List<ProductOptionVM> productOptionList) {
        this.productOptionList = productOptionList;
    }

    public List<ProductCategoryDTO> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategoryDTO> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }

    public List<ProductAddImageVM> getProductAddImageList() {
        return productAddImageList;
    }

    public void setProductAddImageList(List<ProductAddImageVM> productAddImageList) {
        this.productAddImageList = productAddImageList;
    }

    public List<ProductDiscountDTO> getProductDiscountList() {
        return productDiscountList;
    }

    public void setProductDiscountList(List<ProductDiscountDTO> productDiscountList) {
        this.productDiscountList = productDiscountList;
    }

    public List<ProductInputOptionDTO> getProductInputOptionList() {
        return productInputOptionList;
    }

    public void setProductInputOptionList(List<ProductInputOptionDTO> productInputOptionList) {
        this.productInputOptionList = productInputOptionList;
    }

    public List<ProductAddOptionDTO> getProductAddOptionList() {
        return productAddOptionList;
    }

    public void setProductAddOptionList(List<ProductAddOptionDTO> productAddOptionList) {
        this.productAddOptionList = productAddOptionList;
    }

    public List<ProductAnnounceDTO> getProductAnnounceList() {
        return productAnnounceList;
    }

    public void setProductAnnounceList(List<ProductAnnounceDTO> productAnnounceList) {
        this.productAnnounceList = productAnnounceList;
    }

    public List<ProductFaqDTO> getProductFaqList() {
        return productFaqList;
    }

    public void setProductFaqList(List<ProductFaqDTO> productFaqList) {
        this.productFaqList = productFaqList;
    }

    public List<ProductTemplateDTO> getProductTemplateList() {
        return productTemplateList;
    }

    public void setProductTemplateList(List<ProductTemplateDTO> productTemplateList) {
        this.productTemplateList = productTemplateList;
    }

    public List<ProductMappingDTO> getProductMappingList() {
        return productMappingList;
    }

    public void setProductMappingList(List<ProductMappingDTO> productMappingList) {
        this.productMappingList = productMappingList;
    }

    public List<ProductLabelDTO> getProductLabelList() {
        return productLabelList;
    }

    public void setProductLabelList(List<ProductLabelDTO> productLabelList) {
        this.productLabelList = productLabelList;
    }

    @Override
    public String toString() {
        return (
            "ProductVM{" +
            "mainImageFile=" +
            mainImageFile +
            ", mainVideoFile=" +
            mainVideoFile +
            ", descriptionFile=" +
            descriptionFile +
            ", exchangeShippingFile=" +
            exchangeShippingFile +
            ", productOptionList=" +
            productOptionList +
            ", productCategoryList=" +
            productCategoryList +
            ", productAddImageList=" +
            productAddImageList +
            ", productDiscountList=" +
            productDiscountList +
            ", productInputOptionList=" +
            productInputOptionList +
            ", productAddOptionList=" +
            productAddOptionList +
            ", productAnnounceList=" +
            productAnnounceList +
            ", productFaqList=" +
            productFaqList +
            ", productTemplateList=" +
            productTemplateList +
            ", productMappingList=" +
            productMappingList +
            ", productLabelList=" +
            productLabelList +
            '}'
        );
    }
}
