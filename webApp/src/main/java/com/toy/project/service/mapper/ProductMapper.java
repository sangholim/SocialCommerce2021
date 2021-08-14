package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.domain.embedded.EmbeddedProductPrice;
import com.toy.project.domain.embedded.EmbeddedShipment;
import com.toy.project.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        ProductNoticeManageMapper.class,
        ProductClazzAuthorMapper.class,
        ProductStoreMapper.class,
        ProductOptionMapper.class,
        ProductDiscountMapper.class,
        ProductMappingMapper.class,
        ProductAddOptionMapper.class,
        ProductInputOptionMapper.class,
        ProductFaqMapper.class,
        ProductAnnounceMapper.class,
        ProductAddImageMapper.class,
        ProductLabelMapper.class,
        ProductTemplateMapper.class,
        ProductCategoryMapper.class,
        PackageDescriptionMapper.class,
    }
)
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    // 내장 객체 shipment
    @Mapping(target = "shippingReleaseType", source = "embeddedShipment.shippingReleaseType")
    @Mapping(target = "shippingStandardStartTime", source = "embeddedShipment.shippingStandardStartTime")
    @Mapping(target = "etcShippingContent", source = "embeddedShipment.etcShippingContent")
    @Mapping(target = "separateShippingPriceType", source = "embeddedShipment.separateShippingPriceType")
    @Mapping(target = "bundleShippingType", source = "embeddedShipment.bundleShippingType")
    @Mapping(target = "defaultShippingPrice", source = "embeddedShipment.defaultShippingPrice")
    @Mapping(target = "freeShippingPrice", source = "embeddedShipment.freeShippingPrice")
    @Mapping(target = "jejuShippingPrice", source = "embeddedShipment.jejuShippingPrice")
    @Mapping(target = "difficultShippingPrice", source = "embeddedShipment.difficultShippingPrice")
    @Mapping(target = "refundShippingPrice", source = "embeddedShipment.refundShippingPrice")
    @Mapping(target = "exchangeShippingPrice", source = "embeddedShipment.exchangeShippingPrice")
    @Mapping(target = "exchangeShippingFileType", source = "embeddedShipment.exchangeShippingFileType")
    @Mapping(target = "exchangeShippingFileUrl", source = "embeddedShipment.exchangeShippingFileUrl")
    // toEmbeddedProductPrice
    @Mapping(target = "price", source = "embeddedProductPrice.price")
    @Mapping(target = "useInstallment", source = "embeddedProductPrice.useInstallment")
    @Mapping(target = "useDiscountInstant", source = "embeddedProductPrice.useDiscountInstant")
    @Mapping(target = "installmentMonth", source = "embeddedProductPrice.installmentMonth")
    @Mapping(target = "useSellDate", source = "embeddedProductPrice.useSellDate")
    @Mapping(target = "sellDateFrom", source = "embeddedProductPrice.sellDateFrom")
    @Mapping(target = "sellDateTo", source = "embeddedProductPrice.sellDateTo")
    ProductDTO toDto(Product entity);

    // 내장 객체 shipment
    @Mapping(target = "productClazzAuthorId", source = "productClazzAuthor.id")
    @Mapping(target = "productNoticeManage", source = "productNoticeManageId")
    @Mapping(target = "productStoreId", source = "productStore.id")
    @Mapping(target = "embeddedShipment", source = ".", qualifiedByName = "toEmbeddedShipment")
    @Mapping(target = "embeddedProductPrice", source = ".", qualifiedByName = "toEmbeddedProductPrice")
    Product toEntity(ProductDTO productDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoId(Product product);

    default Product fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Product product = new Product();
        product.setId(id);
        return product;
    }

    @Named("toEmbeddedShipment")
    default EmbeddedShipment toEmbeddedShipment(final ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        final EmbeddedShipment embeddedShipment = new EmbeddedShipment();
        embeddedShipment.setShippingReleaseType(productDTO.getShippingReleaseType());
        embeddedShipment.setShippingStandardStartTime(productDTO.getShippingStandardStartTime());
        embeddedShipment.setEtcShippingContent(productDTO.getEtcShippingContent());
        embeddedShipment.setSeparateShippingPriceType(productDTO.getSeparateShippingPriceType());
        embeddedShipment.setBundleShippingType(productDTO.getBundleShippingType());
        embeddedShipment.setDefaultShippingPrice(productDTO.getDefaultShippingPrice());
        embeddedShipment.setFreeShippingPrice(productDTO.getFreeShippingPrice());
        embeddedShipment.setJejuShippingPrice(productDTO.getJejuShippingPrice());
        embeddedShipment.setDifficultShippingPrice(productDTO.getDifficultShippingPrice());
        embeddedShipment.setRefundShippingPrice(productDTO.getRefundShippingPrice());
        embeddedShipment.setExchangeShippingPrice(productDTO.getExchangeShippingPrice());
        embeddedShipment.setExchangeShippingFileType(productDTO.getExchangeShippingFileType());
        embeddedShipment.setExchangeShippingFileUrl(productDTO.getExchangeShippingFileUrl());

        return embeddedShipment;
    }

    @Named("toEmbeddedProductPrice")
    default EmbeddedProductPrice toEmbeddedProductPrice(final ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        final EmbeddedProductPrice embeddedProductPrice = new EmbeddedProductPrice();
        embeddedProductPrice.setPrice(productDTO.getPrice());
        embeddedProductPrice.setInstallmentMonth(productDTO.getInstallmentMonth());
        embeddedProductPrice.setSellDateFrom(productDTO.getSellDateFrom());
        embeddedProductPrice.setSellDateTo(productDTO.getSellDateTo());
        embeddedProductPrice.setUseDiscountInstant(productDTO.getUseDiscountInstant());
        embeddedProductPrice.setUseSellDate(productDTO.getUseSellDate());
        embeddedProductPrice.setUseInstallment(productDTO.getUseInstallment());
        return embeddedProductPrice;
    }
}
