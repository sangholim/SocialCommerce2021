package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.domain.embedded.EmbeddedProductPrice;
import com.toy.project.service.dto.PackageDescriptionDTO;
import com.toy.project.service.dto.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackageDescription} and its DTO {@link PackageDescriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, PackageDescriptionImageMapper.class })
public interface PackageDescriptionMapper extends EntityMapper<PackageDescriptionDTO, PackageDescription> {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "imageUrls", source = ".", qualifiedByName = "toImageUrls")
    @Mapping(target = "imageFileList", ignore = true)
    PackageDescriptionDTO toDto(PackageDescription packageDescription);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PackageDescriptionDTO toDtoId(PackageDescription packageDescription);

    default PackageDescription fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final PackageDescription packageDescription = new PackageDescription();
        packageDescription.setId(id);
        return packageDescription;
    }

    @Named("toImageUrls")
    default List<String> toImageUrls(final PackageDescription packageDescription) {
        return packageDescription
            .getPackageDescriptionImages()
            .stream()
            .filter(packageDescriptionImage -> packageDescriptionImage.getActivated() == true)
            .map(PackageDescriptionImage::getImageUrl)
            .collect(Collectors.toList());
    }
}
