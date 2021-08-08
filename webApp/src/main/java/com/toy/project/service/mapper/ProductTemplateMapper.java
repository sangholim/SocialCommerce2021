package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTemplate} and its DTO {@link ProductTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductTemplateManageMapper.class })
public interface ProductTemplateMapper extends EntityMapper<ProductTemplateDTO, ProductTemplate> {}
