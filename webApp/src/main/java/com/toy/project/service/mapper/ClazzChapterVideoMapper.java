package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClazzChapterVideo} and its DTO {@link ClazzChapterVideoDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClazzChapterMapper.class })
public interface ClazzChapterVideoMapper extends EntityMapper<ClazzChapterVideoDTO, ClazzChapterVideo> {
    @Mapping(target = "clazzChapter", source = "clazzChapterId")
    ClazzChapterVideo toEntity(ClazzChapterVideoDTO dto);

    default ClazzChapterVideo fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final ClazzChapterVideo clazzChapterVideo = new ClazzChapterVideo();
        clazzChapterVideo.setId(id);
        return clazzChapterVideo;
    }
}
