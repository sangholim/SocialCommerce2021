package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductViewContentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductViewContent} and its DTO {@link ProductViewContentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductViewMapper.class })
public interface ProductViewContentMapper extends EntityMapper<ProductViewContentDTO, ProductViewContent> {
    @Mapping(target = "productView", source = "productView", qualifiedByName = "id")
    ProductViewContentDTO toDto(ProductViewContent s);
}
