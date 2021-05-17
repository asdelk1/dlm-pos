package com.dlm.dlmpos.dto;

import com.dlm.dlmpos.entity.SaleDetail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SaleDTO {

    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal total;
    List<SaleDetailDTO> details;
}
