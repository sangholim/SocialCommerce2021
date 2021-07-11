package com.toy.project.service.dto;

import java.util.Set;

/**
 * 상품 및 상품 관련키 관리하는 클래스
 */
public class ProductExtendDTO extends ProductDTO {

    private Set<ProductOptionDTO> productOptions;

    private Set<ProductLabelExtendDTO> productLabels;

    private Set<ProductNoticeDTO> productNotices;

    private Set<ProductShippingDTO> productShippings;

    private Set<ProductTemplateDTO> productTemplates;

    private Set<ProductMappingDTO> productMappings;

    private Set<StoreExtendDTO> stores;

    private Set<ProductCategoryDTO> productCategories;

    private Set<ProductViewDTO> productViews;

    private Set<ClazzExtendDTO> clazzs;

    public ProductExtendDTO() {}

    public Set<ProductOptionDTO> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(Set<ProductOptionDTO> productOptions) {
        this.productOptions = productOptions;
    }

    public Set<ProductNoticeDTO> getProductNotices() {
        return productNotices;
    }

    public void setProductNotices(Set<ProductNoticeDTO> productNotices) {
        this.productNotices = productNotices;
    }

    public Set<ProductShippingDTO> getProductShippings() {
        return productShippings;
    }

    public void setProductShippings(Set<ProductShippingDTO> productShippings) {
        this.productShippings = productShippings;
    }

    public Set<ProductTemplateDTO> getProductTemplates() {
        return productTemplates;
    }

    public void setProductTemplates(Set<ProductTemplateDTO> productTemplates) {
        this.productTemplates = productTemplates;
    }

    public Set<ProductMappingDTO> getProductMappings() {
        return productMappings;
    }

    public void setProductMappings(Set<ProductMappingDTO> productMappings) {
        this.productMappings = productMappings;
    }

    public Set<ProductCategoryDTO> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Set<ProductCategoryDTO> productCategories) {
        this.productCategories = productCategories;
    }

    public Set<ProductViewDTO> getProductViews() {
        return productViews;
    }

    public void setProductViews(Set<ProductViewDTO> productViews) {
        this.productViews = productViews;
    }

    public Set<ProductLabelExtendDTO> getProductLabels() {
        return productLabels;
    }

    public void setProductLabels(Set<ProductLabelExtendDTO> productLabels) {
        this.productLabels = productLabels;
    }

    public Set<StoreExtendDTO> getStores() {
        return stores;
    }

    public void setStores(Set<StoreExtendDTO> stores) {
        this.stores = stores;
    }

    public Set<ClazzExtendDTO> getClazzs() {
        return clazzs;
    }

    public void setClazzs(Set<ClazzExtendDTO> clazzs) {
        this.clazzs = clazzs;
    }
}
