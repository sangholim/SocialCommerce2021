package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOptionRel} and its DTO {@link ProductOptionRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductOptionMapper.class })
public interface ProductOptionRelMapper extends EntityMapper<ProductOptionRelDTO, ProductOptionRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productOption", source = "productOption", qualifiedByName = "id")
    ProductOptionRelDTO toDto(ProductOptionRel s);
}
