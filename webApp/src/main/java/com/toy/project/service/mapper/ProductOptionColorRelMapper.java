package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionColorRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOptionColorRel} and its DTO {@link ProductOptionColorRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductOptionMapper.class, OptionColorMapper.class })
public interface ProductOptionColorRelMapper extends EntityMapper<ProductOptionColorRelDTO, ProductOptionColorRel> {
    @Mapping(target = "productOption", source = "productOption", qualifiedByName = "id")
    @Mapping(target = "optionColor", source = "optionColor", qualifiedByName = "id")
    ProductOptionColorRelDTO toDto(ProductOptionColorRel s);
}
