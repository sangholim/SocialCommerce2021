package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ProductClazzAuthorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductClazzAuthor} and its DTO {@link ProductClazzAuthorDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClazzMapper.class, AuthorMapper.class })
public interface ProductClazzAuthorMapper extends EntityMapper<ProductClazzAuthorDTO, ProductClazzAuthor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductClazzAuthorDTO toDtoId(ProductClazzAuthor productClazzAuthor);

    @Mapping(target = "clazz", source = "clazzId")
    @Mapping(target = "author", source = "authorId")
    ProductClazzAuthor toEntity(ProductClazzAuthorDTO productClazzAuthorDTO);
}
