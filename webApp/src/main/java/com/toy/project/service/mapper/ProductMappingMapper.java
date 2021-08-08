package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductMapping} and its DTO {@link ProductMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMappingManageMapper.class, ProductMapper.class })
public interface ProductMappingMapper extends EntityMapper<ProductMappingDTO, ProductMapping> {}
