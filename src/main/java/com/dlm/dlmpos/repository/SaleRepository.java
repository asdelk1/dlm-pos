package com.dlm.dlmpos.repository;

import com.dlm.dlmpos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {


    List<Sale> findTop30ByOrderByDateDesc();

    @Query("FROM Sale s WHERE (:from IS NULL OR s.date >= :from) AND (:to IS NULL OR s.date <= :to) ORDER BY s.date DESC")
    List<Sale> findPreviousSales(LocalDate from, LocalDate to);
}
