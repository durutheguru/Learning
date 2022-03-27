package com.julianduru.learning.reactive.ms.orderreactiveservice.repository;

import com.julianduru.learning.reactive.ms.orderreactiveservice.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by julian on 23/03/2022
 */
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {


    List<PurchaseOrder> findByUserId(int userId);


}
