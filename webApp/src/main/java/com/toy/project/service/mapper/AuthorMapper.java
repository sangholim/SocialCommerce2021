package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.AuthorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthorDTO toDtoId(Author author);

    default Author fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Author author = new Author();
        author.setId(id);
        return author;
    }
}
