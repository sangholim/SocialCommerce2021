package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductNoticeManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductNoticeManage} and its DTO {@link ProductNoticeManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductNoticeManageMapper extends EntityMapper<ProductNoticeManageDTO, ProductNoticeManage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductNoticeManageDTO toDtoId(ProductNoticeManage productNoticeManage);

    default ProductNoticeManage fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ProductNoticeManage productNoticeManage = new ProductNoticeManage();
        productNoticeManage.setId(id);
        return productNoticeManage;
    }
}
