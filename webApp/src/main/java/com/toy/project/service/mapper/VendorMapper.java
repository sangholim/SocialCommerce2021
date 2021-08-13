package com.toy.project.service.mapper;

import com.toy.project.domain.*;
import com.toy.project.service.dto.ClazzChapterDTO;
import com.toy.project.service.dto.VendorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendor} and its DTO {@link VendorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VendorMapper extends EntityMapper<VendorDTO, Vendor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendorDTO toDtoId(Vendor vendor);

    default Vendor fromId(final Long id) {
        if (id == null) {
            return null;
        }
        final Vendor vendor = new Vendor();
        vendor.setId(id);
        return vendor;
    }
}
