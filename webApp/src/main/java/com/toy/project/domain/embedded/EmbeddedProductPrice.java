package com.toy.project.domain.embedded;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbeddedProductPrice {

    @Column(name = "price")
    private Integer price;

    @Column(name = "use_discount_instant")
    private Boolean useDiscountInstant;

    @Column(name = "use_installment")
    private Boolean useInstallment;

    @Column(name = "installment_month")
    private Integer installmentMonth;

    @Column(name = "use_sell_date")
    private Boolean useSellDate;

    @Column(name = "sell_date_from")
    private Instant sellDateFrom;

    @Column(name = "sell_date_to")
    private Instant sellDateTo;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getUseDiscountInstant() {
        return useDiscountInstant;
    }

    public void setUseDiscountInstant(Boolean useDiscountInstant) {
        this.useDiscountInstant = useDiscountInstant;
    }

    public Boolean getUseInstallment() {
        return useInstallment;
    }

    public void setUseInstallment(Boolean useInstallment) {
        this.useInstallment = useInstallment;
    }

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public Boolean getUseSellDate() {
        return useSellDate;
    }

    public void setUseSellDate(Boolean useSellDate) {
        this.useSellDate = useSellDate;
    }

    public Instant getSellDateFrom() {
        return sellDateFrom;
    }

    public void setSellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public Instant getSellDateTo() {
        return sellDateTo;
    }

    public void setSellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    @Override
    public String toString() {
        return (
            "EmbeddedProductPrice{" +
            "price=" +
            price +
            ", useDiscountInstant=" +
            useDiscountInstant +
            ", useInstallment=" +
            useInstallment +
            ", installmentMonth=" +
            installmentMonth +
            ", useSellDate=" +
            useSellDate +
            ", sellDateFrom=" +
            sellDateFrom +
            ", sellDateTo=" +
            sellDateTo +
            '}'
        );
    }
}
