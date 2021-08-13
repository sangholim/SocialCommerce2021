package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAddImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAddImage} and its DTO {@link ProductAddImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductAddImageMapper extends EntityMapper<ProductAddImageDTO, ProductAddImage> {
    @Mapping(target = "product", source = "productId")
    ProductAddImage toEntity(ProductAddImageDTO dto);

    default ProductAddImage fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductAddImage productAddImage = new ProductAddImage();
        productAddImage.setId(id);
        return productAddImage;
    }
}
