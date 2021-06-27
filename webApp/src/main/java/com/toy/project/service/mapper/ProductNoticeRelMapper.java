package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductNoticeRelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductNoticeRel} and its DTO {@link ProductNoticeRelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductNoticeMapper.class })
public interface ProductNoticeRelMapper extends EntityMapper<ProductNoticeRelDTO, ProductNoticeRel> {
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "productNotice", source = "productNotice", qualifiedByName = "id")
    ProductNoticeRelDTO toDto(ProductNoticeRel s);
}
