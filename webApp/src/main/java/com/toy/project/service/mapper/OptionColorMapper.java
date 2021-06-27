package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.OptionColorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptionColor} and its DTO {@link OptionColorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptionColorMapper extends EntityMapper<OptionColorDTO, OptionColor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OptionColorDTO toDtoId(OptionColor optionColor);
}
