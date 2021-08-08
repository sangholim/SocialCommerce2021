package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductTemplateManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTemplateManage} and its DTO {@link ProductTemplateManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductTemplateManageMapper extends EntityMapper<ProductTemplateManageDTO, ProductTemplateManage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductTemplateManageDTO toDtoId(ProductTemplateManage productTemplateManage);
}
