package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionPackageRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOptionPackageRel} and its DTO {@link ProductOptionPackageRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductOptionMapper.class, OptionPackageMapper.class })
public interface ProductOptionPackageRelMapper extends EntityMapper<ProductOptionPackageRelDTO, ProductOptionPackageRel> {
    @Mapping(target = "productOption", source = "productOption", qualifiedByName = "id")
    @Mapping(target = "optionPackage", source = "optionPackage", qualifiedByName = "id")
    ProductOptionPackageRelDTO toDto(ProductOptionPackageRel s);
}
