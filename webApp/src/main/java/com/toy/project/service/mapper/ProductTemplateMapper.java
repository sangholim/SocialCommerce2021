package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTemplate} and its DTO {@link ProductTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductTemplateMapper extends EntityMapper<ProductTemplateDTO, ProductTemplate> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductTemplateDTO toDtoId(ProductTemplate productTemplate);
}
