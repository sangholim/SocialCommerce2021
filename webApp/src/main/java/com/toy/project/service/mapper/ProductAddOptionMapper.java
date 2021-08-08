package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAddOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAddOption} and its DTO {@link ProductAddOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductAddOptionMapper extends EntityMapper<ProductAddOptionDTO, ProductAddOption> {}
