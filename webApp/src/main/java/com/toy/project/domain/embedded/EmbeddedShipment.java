package com.toy.project.domain.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class EmbeddedShipment {

    @Column(name = "shipping_release_type")
    private String shippingReleaseType;

    @Column(name = "shipping_standard_start_time")
    private String shippingStandardStartTime;

    @Size(max = 300)
    @Column(name = "etc_shipping_content", length = 300)
    private String etcShippingContent;

    @Column(name = "separate_shipping_price_type")
    private String separateShippingPriceType;

    @Column(name = "bundle_shipping_type")
    private String bundleShippingType;

    @Column(name = "default_shipping_price")
    private Integer defaultShippingPrice;

    @Column(name = "free_shipping_price")
    private Integer freeShippingPrice;

    @Column(name = "jeju_shipping_price")
    private Integer jejuShippingPrice;

    @Column(name = "difficult_shipping_price")
    private Integer difficultShippingPrice;

    @Column(name = "refund_shipping_price")
    private Integer refundShippingPrice;

    @Column(name = "exchange_shipping_price")
    private Integer exchangeShippingPrice;

    @Column(name = "exchange_shipping_file_type")
    private String exchangeShippingFileType;

    @Column(name = "exchange_shipping_file_url")
    private String exchangeShippingFileUrl;

    public String getShippingReleaseType() {
        return shippingReleaseType;
    }

    public void setShippingReleaseType(String shippingReleaseType) {
        this.shippingReleaseType = shippingReleaseType;
    }

    public String getShippingStandardStartTime() {
        return shippingStandardStartTime;
    }

    public void setShippingStandardStartTime(String shippingStandardStartTime) {
        this.shippingStandardStartTime = shippingStandardStartTime;
    }

    public String getEtcShippingContent() {
        return etcShippingContent;
    }

    public void setEtcShippingContent(String etcShippingContent) {
        this.etcShippingContent = etcShippingContent;
    }

    public String getSeparateShippingPriceType() {
        return separateShippingPriceType;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public String getBundleShippingType() {
        return bundleShippingType;
    }

    public void setBundleShippingType(String bundleShippingType) {
        this.bundleShippingType = bundleShippingType;
    }

    public Integer getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return freeShippingPrice;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return jejuShippingPrice;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return difficultShippingPrice;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return refundShippingPrice;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return exchangeShippingPrice;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public String getExchangeShippingFileType() {
        return exchangeShippingFileType;
    }

    public void setExchangeShippingFileType(String exchangeShippingFileType) {
        this.exchangeShippingFileType = exchangeShippingFileType;
    }

    public String getExchangeShippingFileUrl() {
        return exchangeShippingFileUrl;
    }

    public void setExchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
    }

    @Override
    public String toString() {
        return (
            "EmbeddedShipment{" +
            "shippingReleaseType='" +
            shippingReleaseType +
            '\'' +
            ", shippingStandardStartTime='" +
            shippingStandardStartTime +
            '\'' +
            ", etcShippingContent='" +
            etcShippingContent +
            '\'' +
            ", separateShippingPriceType='" +
            separateShippingPriceType +
            '\'' +
            ", bundleShippingType='" +
            bundleShippingType +
            '\'' +
            ", defaultShippingPrice=" +
            defaultShippingPrice +
            ", freeShippingPrice=" +
            freeShippingPrice +
            ", jejuShippingPrice=" +
            jejuShippingPrice +
            ", difficultShippingPrice=" +
            difficultShippingPrice +
            ", refundShippingPrice=" +
            refundShippingPrice +
            ", exchangeShippingPrice=" +
            exchangeShippingPrice +
            ", exchangeShippingFileType='" +
            exchangeShippingFileType +
            '\'' +
            ", exchangeShippingFileUrl='" +
            exchangeShippingFileUrl +
            '\'' +
            '}'
        );
    }
}
