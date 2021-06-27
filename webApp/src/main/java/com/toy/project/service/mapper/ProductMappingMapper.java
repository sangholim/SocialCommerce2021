package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductMapping} and its DTO {@link ProductMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMappingMapper extends EntityMapper<ProductMappingDTO, ProductMapping> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductMappingDTO toDtoId(ProductMapping productMapping);
}
