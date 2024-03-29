package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SaleDTO {

    private Long id;
    private String timestamp;
    private BigDecimal total;
    private BigDecimal amountReceived;
    private BigDecimal balance;
    private long itemCount;
    private List<SaleDetailDTO> details;
}
