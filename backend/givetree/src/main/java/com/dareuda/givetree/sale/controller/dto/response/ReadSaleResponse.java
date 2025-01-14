package com.dareuda.givetree.sale.controller.dto.response;

import com.dareuda.givetree.sale.domain.SaleDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadSaleResponse {

    private long id;

    private long sellerId;

    private long foundationId;

    private long price;

    private long contribution;

    private String title;

    private String description;

    private List<String> imageUrls;

    private String saleStatus;

    private String productionCondition;

    private Boolean isDirectSale;

    private Boolean isDeliverySale;

    private long hits;

    private LocalDateTime createdDateTime;

    public static ReadSaleResponse from(SaleDetail saleDetail) {
        return ReadSaleResponse.builder()
                .id(saleDetail.getId())
                .sellerId(saleDetail.getSellerId())
                .foundationId(saleDetail.getFoundationId())
                .price(saleDetail.getPrice())
                .contribution(saleDetail.getContribution())
                .title(saleDetail.getTitle())
                .description(saleDetail.getDescription())
                .imageUrls(saleDetail.getImageUrls())
                .saleStatus(saleDetail.getStatus())
                .productionCondition(saleDetail.getProductionCondition())
                .isDirectSale(saleDetail.isDirectSale())
                .isDeliverySale(saleDetail.isDeliverySale())
                .hits(saleDetail.getHits())
                .createdDateTime(saleDetail.getCreatedDateTime())
                .build();
    }
}
