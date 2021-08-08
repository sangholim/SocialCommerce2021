package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductAnnounceTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAnnounceTemplate} and its DTO {@link ProductAnnounceTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductTemplateManageMapper.class })
public interface ProductAnnounceTemplateMapper extends EntityMapper<ProductAnnounceTemplateDTO, ProductAnnounceTemplate> {}
