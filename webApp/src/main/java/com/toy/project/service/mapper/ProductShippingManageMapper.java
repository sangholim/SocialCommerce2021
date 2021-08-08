package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductShippingManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductShippingManage} and its DTO {@link ProductShippingManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductShippingManageMapper extends EntityMapper<ProductShippingManageDTO, ProductShippingManage> {}
