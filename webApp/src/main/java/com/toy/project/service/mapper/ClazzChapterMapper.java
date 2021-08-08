package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzChapterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClazzChapter} and its DTO {@link ClazzChapterDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClazzMapper.class })
public interface ClazzChapterMapper extends EntityMapper<ClazzChapterDTO, ClazzChapter> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClazzChapterDTO toDtoId(ClazzChapter clazzChapter);
}
