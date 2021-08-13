package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductLabelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductLabel} and its DTO {@link ProductLabelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductLabelManageMapper.class })
public interface ProductLabelMapper extends EntityMapper<ProductLabelDTO, ProductLabel> {
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "productLabelManage", source = "productLabelManageId")
    ProductLabel toEntity(ProductLabelDTO dto);
}
