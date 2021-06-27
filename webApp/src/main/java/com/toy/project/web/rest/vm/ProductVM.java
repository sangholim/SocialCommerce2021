package com.toy.project.web.rest.vm;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductDTO;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class ProductVM extends ProductDTO {

    private Set<ProductCategory> productCategorySet = new HashSet<>();

    private Set<ProductLabel> productLabelSet = new HashSet<>();

    private Set<ProductMapping> productMappingSet = new HashSet<>();

    private Set<ProductView> productViewSet = new HashSet<>();

    private Set<ProductNotice> productNoticeSet = new HashSet<>();

    private Set<ProductShipping> productShippingSet = new HashSet<>();

    private Set<ProductTemplate> productTemplateSet = new HashSet<>();

    private Set<ProductOption> productOptionSet = new HashSet<>();

    private Set<Clazz> clazzSet = new HashSet<>();

    private Set<Store> storeSet = new HashSet<>();

    public ProductVM(Product product) {
        if (product == null) {
            return;
        }

        this.setId(product.getId());
        this.setName(product.getName());
        this.setCode(product.getCode());
        this.setCalculation(product.getCalculation());
        this.setCalculationDateFrom(product.getCalculationDateFrom());
        this.setCalculationDateTo(product.getCalculationDateTo());
        this.setPrice(product.getPrice());
        this.setAllPriceUnit(product.getAllPriceUnit());
        this.setDiscount(product.getDiscount());
        this.setDiscountPrice(product.getDiscountPrice());
        this.setDiscountUnit(product.getDiscountUnit());
        this.setDiscountDateFrom(product.getDiscountDateFrom());
        this.setDiscountDateTo(product.getDiscountDateTo());
        this.setIsInstallment(product.getIsInstallment());
        this.setInstallmentMonth(product.getInstallmentMonth());
        this.setIsSell(product.getIsSell());
        this.setSellDateFrom(product.getSellDateFrom());
        this.setSellDateTo(product.getSellDateTo());
        this.setMinPurchaseAmount(product.getMinPurchaseAmount());
        this.setManPurchaseAmount(product.getManPurchaseAmount());
        this.setMainImageFileUrl(product.getMainImageFileUrl());
        this.setAddImageFileUrl(product.getAddImageFileUrl());
        this.setMainVideoFileUrl(product.getMainVideoFileUrl());
        this.setDescriptionFileUrl(product.getDescriptionFileUrl());
        this.setShippingType(product.getShippingType());
        this.setSeparateShippingPriceType(product.getSeparateShippingPriceType());
        this.setDefaultShippingPrice(product.getDefaultShippingPrice());
        this.setFreeShippingPrice(product.getFreeShippingPrice());
        this.setJejuShippingPrice(product.getJejuShippingPrice());
        this.setDifficultShippingPrice(product.getDifficultShippingPrice());
        this.setRefundShippingPrice(product.getRefundShippingPrice());
        this.setExchangeShippingPrice(product.getExchangeShippingPrice());
        this.setExchangeShippingFileUrl(product.getExchangeShippingFileUrl());
        this.setIsView(product.getIsView());
        this.setViewReservationDate(product.getViewReservationDate());
        this.setActivated(product.getActivated());
        this.setCreatedBy(product.getCreatedBy());
        this.setCreatedDate(product.getCreatedDate());
        this.setLastModifiedBy(product.getLastModifiedBy());
        this.setLastModifiedDate(product.getLastModifiedDate());
        Set<ProductCategoryRel> productCategoryRelSet = product.getProductCategoryRels();
        Set<ProductClazzRel> productClazzRelSet = product.getProductClazzRels();
        Set<ProductStoreRel> productStoreRelSet = product.getProductStoreRels();
        Set<ProductLabelRel> productLabelRelSet = product.getProductLabelRels();
        Set<ProductMappingRel> productMappingRelSet = product.getProductMappingRels();
        Set<ProductNoticeRel> productNoticeRelSet = product.getProductNoticeRels();
        Set<ProductShippingRel> productShippingRelSet = product.getProductShippingRels();
        Set<ProductTemplateRel> productTemplateRelSet = product.getProductTemplateRels();
        Set<ProductOptionRel> productOptionRelSet = product.getProductOptionRels();
        Set<ProductViewRel> productViewRelSet = product.getProductViewRels();
        if (!CollectionUtils.isEmpty(productViewRelSet)) {
            productViewRelSet.forEach(
                productViewRel -> {
                    this.productViewSet.add(productViewRel.getProductView());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productOptionRelSet)) {
            productOptionRelSet.forEach(
                productOptionRel -> {
                    this.productOptionSet.add(productOptionRel.getProductOption());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productTemplateRelSet)) {
            productTemplateRelSet.forEach(
                productTemplateRel -> {
                    this.productTemplateSet.add(productTemplateRel.getProductTemplate());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productShippingRelSet)) {
            productShippingRelSet.forEach(
                productShippingRel -> {
                    this.productShippingSet.add(productShippingRel.getProductShipping());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productNoticeRelSet)) {
            productNoticeRelSet.forEach(
                productNoticeRel -> {
                    this.productNoticeSet.add(productNoticeRel.getProductNotice());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productMappingRelSet)) {
            productMappingRelSet.forEach(
                productMappingRel -> {
                    this.productMappingSet.add(productMappingRel.getProductMapping());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productLabelRelSet)) {
            productLabelRelSet.forEach(
                productLabelRel -> {
                    this.productLabelSet.add(productLabelRel.getProductLabel());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productCategoryRelSet)) {
            productCategoryRelSet.forEach(
                productCategoryRel -> {
                    this.productCategorySet.add(productCategoryRel.getProductCategory());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productClazzRelSet)) {
            productClazzRelSet.forEach(
                productClazzRel -> {
                    this.clazzSet.add(productClazzRel.getClazz());
                }
            );
        }
        if (!CollectionUtils.isEmpty(productStoreRelSet)) {
            productStoreRelSet.forEach(
                productStoreRel -> {
                    this.storeSet.add(productStoreRel.getStore());
                }
            );
        }
    }

    public Set<ProductCategory> getProductCategorySet() {
        return productCategorySet;
    }

    public void setProductCategorySet(Set<ProductCategory> productCategorySet) {
        this.productCategorySet = productCategorySet;
    }

    public Set<ProductLabel> getProductLabelSet() {
        return productLabelSet;
    }

    public void setProductLabelSet(Set<ProductLabel> productLabelSet) {
        this.productLabelSet = productLabelSet;
    }

    public Set<ProductMapping> getProductMappingSet() {
        return productMappingSet;
    }

    public void setProductMappingSet(Set<ProductMapping> productMappingSet) {
        this.productMappingSet = productMappingSet;
    }

    public Set<ProductView> getProductViewSet() {
        return productViewSet;
    }

    public void setProductViewSet(Set<ProductView> productViewSet) {
        this.productViewSet = productViewSet;
    }

    public Set<ProductNotice> getProductNoticeSet() {
        return productNoticeSet;
    }

    public void setProductNoticeSet(Set<ProductNotice> productNoticeSet) {
        this.productNoticeSet = productNoticeSet;
    }

    public Set<ProductShipping> getProductShippingSet() {
        return productShippingSet;
    }

    public void setProductShippingSet(Set<ProductShipping> productShippingSet) {
        this.productShippingSet = productShippingSet;
    }

    public Set<ProductTemplate> getProductTemplateSet() {
        return productTemplateSet;
    }

    public void setProductTemplateSet(Set<ProductTemplate> productTemplateSet) {
        this.productTemplateSet = productTemplateSet;
    }

    public Set<ProductOption> getProductOptionSet() {
        return productOptionSet;
    }

    public void setProductOptionSet(Set<ProductOption> productOptionSet) {
        this.productOptionSet = productOptionSet;
    }

    public Set<Clazz> getClazzSet() {
        return clazzSet;
    }

    public void setClazzSet(Set<Clazz> clazzSet) {
        this.clazzSet = clazzSet;
    }

    public Set<Store> getStoreSet() {
        return storeSet;
    }

    public void setStoreSet(Set<Store> storeSet) {
        this.storeSet = storeSet;
    }
}
