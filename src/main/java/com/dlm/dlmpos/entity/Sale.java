package com.dlm.dlmpos.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sale")
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal total;
    private BigDecimal amountReceived;
    private BigDecimal balance;
    @OneToMany(mappedBy = "sale")
    List<SaleDetail> details;
}
