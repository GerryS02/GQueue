package com.gerry.gqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerry.gqueue.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
