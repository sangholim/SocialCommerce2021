package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductClazzRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductClazzRel} and its DTO {@link ProductClazzRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ClazzMapper.class })
public interface ProductClazzRelMapper extends EntityMapper<ProductClazzRelDTO, ProductClazzRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "clazz", source = "clazz", qualifiedByName = "id")
    ProductClazzRelDTO toDto(ProductClazzRel s);
}
