package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductMappingManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductMappingManage} and its DTO {@link ProductMappingManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMappingManageMapper extends EntityMapper<ProductMappingManageDTO, ProductMappingManage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductMappingManageDTO toDtoId(ProductMappingManage productMappingManage);
}
