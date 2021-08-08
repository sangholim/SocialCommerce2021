package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, ProductCategoryManageMapper.class })
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {}
