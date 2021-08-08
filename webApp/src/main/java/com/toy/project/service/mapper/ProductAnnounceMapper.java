package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAnnounceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAnnounce} and its DTO {@link ProductAnnounceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface ProductAnnounceMapper extends EntityMapper<ProductAnnounceDTO, ProductAnnounce> {}
