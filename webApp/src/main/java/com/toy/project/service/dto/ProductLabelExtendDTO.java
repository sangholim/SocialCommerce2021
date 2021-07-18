package com.toy.project.service.dto;

import com.toy.project.domain.ProductLabelRel;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

/**
 * productLabel + 관계테이블 컬럼
 */
public class ProductLabelExtendDTO extends ProductLabelDTO {

    private Boolean isDisplayDate;

    private Instant displayDateFrom;

    private Instant displayDateTo;

    public ProductLabelExtendDTO() {}

    public ProductLabelExtendDTO(ProductLabelRel productLabelRel) {
        super(productLabelRel.getProductLabel());
        if (productLabelRel == null) {
            return;
        }
        this.isDisplayDate = productLabelRel.getIsDisplayDate();
        this.displayDateFrom = productLabelRel.getDisplayDateFrom();
        this.displayDateTo = productLabelRel.getDisplayDateTo();
    }

    public static Set<ProductLabelExtendDTO> toExtendSet(Collection<ProductLabelRel> productLabelRels) {
        if (CollectionUtils.isEmpty(productLabelRels)) {
            return null;
        }
        return productLabelRels.stream().map(ProductLabelExtendDTO::new).collect(Collectors.toSet());
    }

    public static List<ProductLabelExtendDTO> toExtendList(Collection<ProductLabelRel> productLabelRels) {
        if (CollectionUtils.isEmpty(productLabelRels)) {
            return null;
        }
        return productLabelRels.stream().map(ProductLabelExtendDTO::new).collect(Collectors.toList());
    }

    public Boolean getDisplayDate() {
        return isDisplayDate;
    }

    public void setDisplayDate(Boolean displayDate) {
        isDisplayDate = displayDate;
    }

    public Instant getDisplayDateFrom() {
        return displayDateFrom;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return displayDateTo;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
    }
}
