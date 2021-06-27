package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductNoticeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductNotice} and its DTO {@link ProductNoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductNoticeMapper extends EntityMapper<ProductNoticeDTO, ProductNotice> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductNoticeDTO toDtoId(ProductNotice productNotice);
}
