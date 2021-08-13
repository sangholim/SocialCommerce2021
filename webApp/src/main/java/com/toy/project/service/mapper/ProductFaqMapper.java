package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductFaqDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductFaq} and its DTO {@link ProductFaqDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductFaqMapper extends EntityMapper<ProductFaqDTO, ProductFaq> {
    @Mapping(target = "product", source = "productId")
    ProductFaq toEntity(ProductFaqDTO productFaqDTO);
}
