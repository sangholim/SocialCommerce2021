package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductTemplateRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTemplateRel} and its DTO {@link ProductTemplateRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductTemplateMapper.class })
public interface ProductTemplateRelMapper extends EntityMapper<ProductTemplateRelDTO, ProductTemplateRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productTemplate", source = "productTemplate", qualifiedByName = "id")
    ProductTemplateRelDTO toDto(ProductTemplateRel s);
}
