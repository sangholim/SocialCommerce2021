package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductInputOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductInputOption} and its DTO {@link ProductInputOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductInputOptionMapper extends EntityMapper<ProductInputOptionDTO, ProductInputOption> {}
