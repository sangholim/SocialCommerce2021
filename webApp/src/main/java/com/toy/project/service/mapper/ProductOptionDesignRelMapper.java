package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductOptionDesignRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOptionDesignRel} and its DTO {@link ProductOptionDesignRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductOptionMapper.class, OptionDesignMapper.class })
public interface ProductOptionDesignRelMapper extends EntityMapper<ProductOptionDesignRelDTO, ProductOptionDesignRel> {
    @Mapping(target = "productOption", source = "productOption", qualifiedByName = "id")
    @Mapping(target = "optionDesign", source = "optionDesign", qualifiedByName = "id")
    ProductOptionDesignRelDTO toDto(ProductOptionDesignRel s);
}
