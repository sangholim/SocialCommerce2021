package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductStoreRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductStoreRel} and its DTO {@link ProductStoreRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, StoreMapper.class })
public interface ProductStoreRelMapper extends EntityMapper<ProductStoreRelDTO, ProductStoreRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "store", source = "store", qualifiedByName = "id")
    ProductStoreRelDTO toDto(ProductStoreRel s);
}
