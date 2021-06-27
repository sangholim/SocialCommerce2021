package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductMappingRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductMappingRel} and its DTO {@link ProductMappingRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductMappingMapper.class })
public interface ProductMappingRelMapper extends EntityMapper<ProductMappingRelDTO, ProductMappingRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productMapping", source = "productMapping", qualifiedByName = "id")
    ProductMappingRelDTO toDto(ProductMappingRel s);
}
