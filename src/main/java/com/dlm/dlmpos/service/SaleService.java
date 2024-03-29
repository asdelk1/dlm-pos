package com.dlm.dlmpos.service;

import com.dlm.dlmpos.dto.Receipt;
import com.dlm.dlmpos.dto.ShoppingCartDTO;
import com.dlm.dlmpos.dto.ShoppingCartItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.entity.Sale;
import com.dlm.dlmpos.entity.SaleDetail;
import com.dlm.dlmpos.repository.SaleDetailRepository;
import com.dlm.dlmpos.repository.SaleRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SaleService {

    private final ItemService itemService;
    private final SaleDetailRepository repository;
    private final SaleRepository saleRepository;

    public SaleService(ItemService itemService, SaleDetailRepository repository, SaleRepository saleRepository) {
        this.itemService = itemService;
        this.repository = repository;
        this.saleRepository = saleRepository;
    }

    public Sale saveShoppingCart(ShoppingCartDTO cart) {

        Sale sale = new Sale();
        sale.setTimestamp(LocalDateTime.now());
        sale.setDate(LocalDate.now());
        sale = this.saleRepository.save(sale);

        BigDecimal totalBill = BigDecimal.ZERO;

        List<SaleDetail> saleDetailList = new ArrayList<>();
        for (ShoppingCartItemDTO itemInCart : cart.getItems()) {
            Optional<Item> item = this.itemService.get(itemInCart.getItem().getId());
            if (item.isPresent()) {
                long qty = itemInCart.getQty();
                BigDecimal unitPrice = item.get().getUnitPrice();
                BigDecimal total = unitPrice.multiply(new BigDecimal(qty));

                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setItem(item.get());
                saleDetail.setUnitPrice(unitPrice);
                saleDetail.setTotal(total);
                saleDetail.setSale(sale);
                saleDetail.setQty(itemInCart.getQty());
                saleDetailList.add(saleDetail);

                totalBill = totalBill.add(total);
            }
        }

        if (saleDetailList.size() > 0) {
            sale.setTotal(totalBill);
            sale.setAmountReceived(BigDecimal.valueOf(cart.getMoneyReceived()));
            sale.setBalance(sale.getAmountReceived().subtract(sale.getTotal()));
            this.saleRepository.save(sale);
            this.repository.saveAll(saleDetailList);
        }

        return sale;
    }

    public void exportReceipt(long id, OutputStream out) throws JRException {

        Optional<Sale> saleOpt = this.saleRepository.findById(id);
        if (saleOpt.isEmpty()) {
            return;
        }

        Sale sale = saleOpt.get();
        InputStream is = this.getClass().getResourceAsStream("/reports/thermal.jrxml");
        final JasperReport reports = JasperCompileManager.compileReport(is);

        Receipt receipt = new Receipt(
                sale.getId(),
                sale.getTotal(),
                sale.getAmountReceived(),
                sale.getBalance(),
                sale.getDetails());

        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(receipt));
        final Map<String, Object> params = new HashMap<>();
        params.put("timestamp", sale.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE));
        params.put("totalBill", sale.getTotal().toPlainString());

        JasperPrint print = JasperFillManager.fillReport(reports, params, source);
        JasperExportManager.exportReportToPdfStream(print, out);
    }

    public void exportReceipt(long id) throws JRException {
        Optional<Sale> saleOpt = this.saleRepository.findById(id);
        if (saleOpt.isEmpty()) {
            return;
        }

        Sale sale = saleOpt.get();
        InputStream is = this.getClass().getResourceAsStream("/reports/thermal.jrxml");
        final JasperReport reports = JasperCompileManager.compileReport(is);

        Receipt receipt = new Receipt(
                sale.getId(),
                sale.getTotal(),
                sale.getAmountReceived(),
                sale.getBalance(),
                sale.getDetails());

        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singleton(receipt));
        final Map<String, Object> params = new HashMap<>();
        params.put("timestamp", sale.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE));
        params.put("totalBill", sale.getTotal().toPlainString());

        JasperPrint print = JasperFillManager.fillReport(reports, params, source);
        this.directPrint(print);
    }

    public void directPrint(JasperPrint print) throws JRException {

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(OrientationRequested.PORTRAIT);

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        printServiceAttributeSet.add(new PrinterName(defaultPrintService.getName(), null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);

        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setConfiguration(configuration);

//        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//        PrintService printService = null;
//        if(services.length != 0 && services != null){
//            printService = services[0];
////            for(PrintService service : services){
////               service.getName();
////            }
//        }

        if(defaultPrintService != null){
            exporter.exportReport();
        }
    }

    public List<Sale> getHistory(LocalDate from, LocalDate to){

        List<Sale> sales;
        if(from == null && to == null){
            sales = this.saleRepository.findTop30ByOrderByDateDesc();
        }else{
            sales = this.saleRepository.findPreviousSales(from, to);
        }
        return sales;
    }

    public Optional<Sale> getSale(long id){

        return this.saleRepository.findById(id);
    }
}
