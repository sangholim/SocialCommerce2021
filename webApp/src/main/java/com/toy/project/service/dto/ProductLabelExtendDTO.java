package com.toy.project.service.dto;

import java.time.Instant;

/**
 * productLabel + 관계테이블 컬럼
 */
public class ProductLabelExtendDTO extends ProductLabelDTO {

    private Boolean isDisplayDate;

    private Instant displayDateFrom;

    private Instant displayDateTo;

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
