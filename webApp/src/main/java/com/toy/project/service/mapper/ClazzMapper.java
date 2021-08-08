package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clazz} and its DTO {@link ClazzDTO}.
 */
@Mapper(componentModel = "spring", uses = { AuthorMapper.class })
public interface ClazzMapper extends EntityMapper<ClazzDTO, Clazz> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClazzDTO toDtoId(Clazz clazz);
}
