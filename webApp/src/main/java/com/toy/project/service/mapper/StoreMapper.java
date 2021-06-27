package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.StoreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Store} and its DTO {@link StoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper extends EntityMapper<StoreDTO, Store> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StoreDTO toDtoId(Store store);
}
