package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAddOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAddOption} and its DTO {@link ProductAddOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductAddOptionMapper extends EntityMapper<ProductAddOptionDTO, ProductAddOption> {
    @Mapping(target = "product", source = "productId")
    ProductAddOption toEntity(ProductAddOptionDTO productAddOptionDTO);

    default ProductAddOption fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductAddOption productAddOption = new ProductAddOption();
        productAddOption.setId(id);
        return productAddOption;
    }
}
