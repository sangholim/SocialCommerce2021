package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.OptionDesignDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptionDesign} and its DTO {@link OptionDesignDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptionDesignMapper extends EntityMapper<OptionDesignDTO, OptionDesign> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OptionDesignDTO toDtoId(OptionDesign optionDesign);
}
