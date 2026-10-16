package com.gerry.gqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gerry.gqueue.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
