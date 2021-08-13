package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzChapterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClazzChapter} and its DTO {@link ClazzChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClazzMapper.class })
public interface ClazzChapterMapper extends EntityMapper<ClazzChapterDTO, ClazzChapter> {
    ClazzChapterDTO toDto(ClazzChapter entity);

    @Mapping(target = "clazz", source = "clazzId")
    ClazzChapter toEntity(ClazzChapterDTO dto);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClazzChapterDTO toDtoId(ClazzChapter clazzChapter);

    default ClazzChapter fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ClazzChapter clazzChapter = new ClazzChapter();
        clazzChapter.setId(id);
        return clazzChapter;
    }
}
