package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductMapping} and its DTO {@link ProductMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMappingManageMapper.class, ProductMapper.class })
public interface ProductMappingMapper extends EntityMapper<ProductMappingDTO, ProductMapping> {
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "productMappingManage", source = "productMappingManageId")
    ProductMapping toEntity(ProductMappingDTO dto);

    default ProductMapping fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductMapping productMapping = new ProductMapping();
        productMapping.setId(id);
        return productMapping;
    }
}
