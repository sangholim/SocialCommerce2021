package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductShippingManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductShippingManage} and its DTO {@link ProductShippingManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductShippingManageMapper extends EntityMapper<ProductShippingManageDTO, ProductShippingManage> {
    default ProductShippingManage fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductShippingManage productShippingManage = new ProductShippingManage();
        productShippingManage.setId(id);
        return productShippingManage;
    }
}
