package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAddImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAddImage} and its DTO {@link ProductAddImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductAddImageMapper extends EntityMapper<ProductAddImageDTO, ProductAddImage> {}
