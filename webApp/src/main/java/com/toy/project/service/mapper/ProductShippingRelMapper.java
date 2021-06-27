package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductShippingRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductShippingRel} and its DTO {@link ProductShippingRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductShippingMapper.class })
public interface ProductShippingRelMapper extends EntityMapper<ProductShippingRelDTO, ProductShippingRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productShipping", source = "productShipping", qualifiedByName = "id")
    ProductShippingRelDTO toDto(ProductShippingRel s);
}
