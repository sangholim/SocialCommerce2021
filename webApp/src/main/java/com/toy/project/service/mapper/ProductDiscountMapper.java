package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductDiscountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDiscount} and its DTO {@link ProductDiscountDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductDiscountMapper extends EntityMapper<ProductDiscountDTO, ProductDiscount> {
    @Mapping(target = "product", source = "productId")
    ProductDiscount toEntity(ProductDiscountDTO productDiscountDTO);
}
