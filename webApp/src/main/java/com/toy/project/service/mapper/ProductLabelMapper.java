package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductLabelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductLabel} and its DTO {@link ProductLabelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductLabelMapper extends EntityMapper<ProductLabelDTO, ProductLabel> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductLabelDTO toDtoId(ProductLabel productLabel);
}
