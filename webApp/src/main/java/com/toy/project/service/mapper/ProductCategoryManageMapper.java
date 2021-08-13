package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductCategoryManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategoryManage} and its DTO {@link ProductCategoryManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryManageMapper extends EntityMapper<ProductCategoryManageDTO, ProductCategoryManage> {
    default ProductCategoryManage fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductCategoryManage productCategoryManage = new ProductCategoryManage();
        productCategoryManage.setId(id);
        return productCategoryManage;
    }
}
