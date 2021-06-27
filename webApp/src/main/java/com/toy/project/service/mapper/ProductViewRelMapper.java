package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductViewRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductViewRel} and its DTO {@link ProductViewRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductViewMapper.class })
public interface ProductViewRelMapper extends EntityMapper<ProductViewRelDTO, ProductViewRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productView", source = "productView", qualifiedByName = "id")
    ProductViewRelDTO toDto(ProductViewRel s);
}
