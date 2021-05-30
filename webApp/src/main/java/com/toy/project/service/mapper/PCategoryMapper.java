package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.PCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PCategory} and its DTO {@link PCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PCategoryMapper extends EntityMapper<PCategoryDTO, PCategory> {}
