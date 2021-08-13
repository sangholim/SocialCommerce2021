package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTemplate} and its DTO {@link ProductTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductTemplateManageMapper.class })
public interface ProductTemplateMapper extends EntityMapper<ProductTemplateDTO, ProductTemplate> {
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "productTemplateManage", source = "productTemplateManageId")
    ProductTemplate toEntity(ProductTemplateDTO dto);

    default ProductTemplate fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductTemplate productTemplate = new ProductTemplate();
        productTemplate.setId(id);
        return productTemplate;
    }
}
