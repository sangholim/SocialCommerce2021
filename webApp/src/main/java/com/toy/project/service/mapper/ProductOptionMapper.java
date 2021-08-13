package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOption} and its DTO {@link ProductOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductOptionMapper extends EntityMapper<ProductOptionDTO, ProductOption> {
    @Mapping(target = "product", source = "productId")
    ProductOption toEntity(ProductOptionDTO productOptionDTO);

    @Mapping(target = "packageThumbnail", ignore = true)
    ProductOptionDTO toDto(ProductOption productOption);

    default ProductOption fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductOption productOption = new ProductOption();
        productOption.setId(id);
        return productOption;
    }
}
