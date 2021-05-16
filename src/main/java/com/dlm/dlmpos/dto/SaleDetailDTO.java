package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleDetailDTO {

    private Long id;
    private ItemDTO item;
    private BigDecimal unitPrice;
    private long qty;
    private BigDecimal total;
}
