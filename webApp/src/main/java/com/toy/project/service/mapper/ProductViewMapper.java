package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductView} and its DTO {@link ProductViewDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductViewMapper extends EntityMapper<ProductViewDTO, ProductView> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductViewDTO toDtoId(ProductView productView);
}
