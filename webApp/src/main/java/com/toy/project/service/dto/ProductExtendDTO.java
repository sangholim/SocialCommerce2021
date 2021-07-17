package com.toy.project.service.dto;

import com.toy.project.domain.Product;
import java.util.List;

/**
 * 상품 및 상품 관련키 관리하는 클래스
 */
public class ProductExtendDTO extends ProductDTO {

    private List<ProductOptionDTO> productOptions;

    private List<ProductLabelExtendDTO> productLabels;

    private List<ProductNoticeDTO> productNotices;

    private List<ProductShippingDTO> productShippings;

    private List<ProductTemplateDTO> productTemplates;

    private List<ProductMappingDTO> productMappings;

    private List<StoreExtendDTO> stores;

    private List<ProductCategoryDTO> productCategories;

    private List<ProductViewDTO> productViews;

    private List<ClazzExtendDTO> clazzs;

    public ProductExtendDTO() {}

    public ProductExtendDTO(Product product) {
        super(product);
        if (product == null) {
            return;
        }
        this.productViews = ProductViewDTO.toList(product.getProductViews());
        this.productOptions = ProductOptionDTO.toList(product.getProductOptions());
        this.productTemplates = ProductTemplateDTO.toList(product.getProductTemplates());
        this.productShippings = ProductShippingDTO.toList(product.getProductShippings());
        this.productMappings = ProductMappingDTO.toList(product.getProductMappings());
        this.productCategories = ProductCategoryDTO.toList(product.getProductCategories());
        this.productNotices = ProductNoticeDTO.toList(product.getProductNotices());
        // 관계테이블에 외래키 이외의 컬럼이 정의된 경우
        this.clazzs = ClazzExtendDTO.toExtendList(product.getProductClazzRels());
        this.stores = StoreExtendDTO.toExtendList(product.getProductStoreRels());
        this.productLabels = ProductLabelExtendDTO.toExtendList(product.getProductLabelRels());
    }

    public List<ProductOptionDTO> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptionDTO> productOptions) {
        this.productOptions = productOptions;
    }

    public List<ProductLabelExtendDTO> getProductLabels() {
        return productLabels;
    }

    public void setProductLabels(List<ProductLabelExtendDTO> productLabels) {
        this.productLabels = productLabels;
    }

    public List<ProductNoticeDTO> getProductNotices() {
        return productNotices;
    }

    public void setProductNotices(List<ProductNoticeDTO> productNotices) {
        this.productNotices = productNotices;
    }

    public List<ProductShippingDTO> getProductShippings() {
        return productShippings;
    }

    public void setProductShippings(List<ProductShippingDTO> productShippings) {
        this.productShippings = productShippings;
    }

    public List<ProductTemplateDTO> getProductTemplates() {
        return productTemplates;
    }

    public void setProductTemplates(List<ProductTemplateDTO> productTemplates) {
        this.productTemplates = productTemplates;
    }

    public List<ProductMappingDTO> getProductMappings() {
        return productMappings;
    }

    public void setProductMappings(List<ProductMappingDTO> productMappings) {
        this.productMappings = productMappings;
    }

    public List<StoreExtendDTO> getStores() {
        return stores;
    }

    public void setStores(List<StoreExtendDTO> stores) {
        this.stores = stores;
    }

    public List<ProductCategoryDTO> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategoryDTO> productCategories) {
        this.productCategories = productCategories;
    }

    public List<ProductViewDTO> getProductViews() {
        return productViews;
    }

    public void setProductViews(List<ProductViewDTO> productViews) {
        this.productViews = productViews;
    }

    public List<ClazzExtendDTO> getClazzs() {
        return clazzs;
    }

    public void setClazzs(List<ClazzExtendDTO> clazzs) {
        this.clazzs = clazzs;
    }
}
