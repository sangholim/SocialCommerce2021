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

    @Mapping(target = "author", source = "authorId")
    Clazz toEntity(ClazzDTO clazzDTO);

    default Clazz fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Clazz clazz = new Clazz();
        clazz.setId(id);
        return clazz;
    }
}
