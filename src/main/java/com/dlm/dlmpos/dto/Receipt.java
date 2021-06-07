package com.dlm.dlmpos.dto;

import com.dlm.dlmpos.entity.SaleDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class Receipt {

    private Long id;
    private String timestamp;
    private BigDecimal total;
    private BigDecimal balance;
    private BigDecimal receivedAmount;
    private List<SaleDetail> items;

    public Receipt(Long id,
                   BigDecimal total,
                   BigDecimal balance,
                   BigDecimal receivedAmount,
                   List<SaleDetail> items) {
        this.id = id;
        this.total = total;
        this.receivedAmount = receivedAmount;
        this.balance = balance;
        this.items = items;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
