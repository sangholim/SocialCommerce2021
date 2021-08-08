package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductLabelManageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductLabelManage} and its DTO {@link ProductLabelManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductLabelManageMapper extends EntityMapper<ProductLabelManageDTO, ProductLabelManage> {}
