package com.dlm.dlmpos.repository;

import com.dlm.dlmpos.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
