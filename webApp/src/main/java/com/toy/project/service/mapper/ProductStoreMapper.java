package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductStoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductStore} and its DTO {@link ProductStoreDTO}.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class, BrandMapper.class })
public interface ProductStoreMapper extends EntityMapper<ProductStoreDTO, ProductStore> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductStoreDTO toDtoId(ProductStore productStore);
}
