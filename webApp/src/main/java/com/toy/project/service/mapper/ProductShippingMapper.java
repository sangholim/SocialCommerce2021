package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductShippingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductShipping} and its DTO {@link ProductShippingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductShippingMapper extends EntityMapper<ProductShippingDTO, ProductShipping> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductShippingDTO toDtoId(ProductShipping productShipping);
}
