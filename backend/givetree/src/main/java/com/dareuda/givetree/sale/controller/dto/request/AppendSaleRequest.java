package com.dareuda.givetree.sale.controller.dto.request;

import com.dareuda.givetree.sale.domain.ProductionCondition;
import com.dareuda.givetree.sale.domain.SaleCommand;
import com.dareuda.givetree.sale.domain.SaleStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
public class AppendSaleRequest {

    @Min(1)
    private Long foundationId;

    @Min(0)
    private Long price;

    @Range(min = 0, max = 100)
    private Integer donationRate;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private List<String> imageUrls;

    @NotBlank
    private String status;

    @NotBlank
    private String productionCondition;

    @NotNull
    private Boolean isDirectSale;

    @NotNull
    private Boolean isDeliverySale;

    public SaleCommand toCommand() {
        return SaleCommand.builder()
                .foundationId(foundationId)
                .price(price)
                .donationRate(donationRate)
                .title(title)
                .description(description)
                .imageUrls(imageUrls)
                .status(SaleStatus.of(status))
                .productionCondition(ProductionCondition.of(productionCondition))
                .isDirectSale(isDirectSale)
                .isDeliverySale(isDeliverySale)
                .build();
    }
}
