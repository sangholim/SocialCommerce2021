package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.OptionPackageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OptionPackage} and its DTO {@link OptionPackageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OptionPackageMapper extends EntityMapper<OptionPackageDTO, OptionPackage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OptionPackageDTO toDtoId(OptionPackage optionPackage);
}
