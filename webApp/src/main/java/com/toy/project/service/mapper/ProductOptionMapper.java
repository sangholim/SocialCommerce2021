package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOption} and its DTO {@link ProductOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductOptionMapper extends EntityMapper<ProductOptionDTO, ProductOption> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductOptionDTO toDtoId(ProductOption productOption);
}
