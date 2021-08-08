package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClazzChapterVideo} and its DTO {@link ClazzChapterVideoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClazzChapterMapper.class })
public interface ClazzChapterVideoMapper extends EntityMapper<ClazzChapterVideoDTO, ClazzChapterVideo> {}
