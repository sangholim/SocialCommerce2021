package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductLabelRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductLabelRel} and its DTO {@link ProductLabelRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductLabelMapper.class })
public interface ProductLabelRelMapper extends EntityMapper<ProductLabelRelDTO, ProductLabelRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productLabel", source = "productLabel", qualifiedByName = "id")
    ProductLabelRelDTO toDto(ProductLabelRel s);
}
