package com.dlm.dlmpos.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Item item;
    private BigDecimal unitPrice;
    private long qty;
    private BigDecimal total;
    @ManyToOne
    private Sale sale;

}
