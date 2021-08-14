package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.PackageDescriptionImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackageDescriptionImage} and its DTO {@link PackageDescriptionImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { PackageDescriptionMapper.class })
public interface PackageDescriptionImageMapper extends EntityMapper<PackageDescriptionImageDTO, PackageDescriptionImage> {
    @Mapping(target = "packageDescriptionId", source = "packageDescription.id")
    PackageDescriptionImageDTO toDto(PackageDescriptionImage s);

    default PackageDescriptionImage fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final PackageDescriptionImage packageDescriptionImage = new PackageDescriptionImage();
        packageDescriptionImage.setId(id);
        return packageDescriptionImage;
    }
}
