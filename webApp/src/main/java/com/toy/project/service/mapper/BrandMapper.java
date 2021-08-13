package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.BrandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoId(Brand brand);

    default Brand fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Brand brand = new Brand();
        brand.setId(id);
        return brand;
    }
}
