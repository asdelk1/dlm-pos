package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.SaleDTO;
import com.dlm.dlmpos.dto.ShoppingCartDTO;
import com.dlm.dlmpos.dto.ShoppingCartItemDTO;
import com.dlm.dlmpos.entity.Sale;
import com.dlm.dlmpos.service.SaleService;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;
    private final ModelMapper mapper;

    public SaleController(SaleService saleService,
                          ModelMapper mapper) {
        this.saleService = saleService;
        this.mapper = mapper;
    }

    @PostMapping("")
    public ResponseEntity<URI> addSale(@RequestBody ShoppingCartDTO sale){

        this.saleService.saveShoppingCart(sale);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}/print")
    public void printReceipt(HttpServletResponse response,@PathVariable long id) throws IOException, JRException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"receipt.pdf\"");
        OutputStream out = response.getOutputStream();
        this.saleService.exportReceipt(id, out);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SaleDTO>> getHistory(@PathParam("start") String start, @PathParam("end") String end){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = start != null ? LocalDate.parse(start,dateFormatter) : null;
        LocalDate endDate = end != null ? LocalDate.parse(end,dateFormatter) : null;

        List<Sale> saleList = this.saleService.getHistory();
        List<SaleDTO> dtoList = saleList.stream().map(s -> {
            SaleDTO dto = this.mapper.map(s, SaleDTO.class);
            dto.setItemCount(s.getDetails().size());
            dto.setTimestamp(s.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}
