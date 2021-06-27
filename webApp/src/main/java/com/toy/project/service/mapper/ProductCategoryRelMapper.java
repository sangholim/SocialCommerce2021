package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductCategoryRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategoryRel} and its DTO {@link ProductCategoryRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductCategoryMapper.class })
public interface ProductCategoryRelMapper extends EntityMapper<ProductCategoryRelDTO, ProductCategoryRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productCategory", source = "productCategory", qualifiedByName = "id")
    ProductCategoryRelDTO toDto(ProductCategoryRel s);
}
