package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.Order;
import com.github.maciejiwan.investmens_tracking.entity.Part;
import com.github.maciejiwan.investmens_tracking.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByLineItems_Part(Part part1);

    List<Order> findByBuyer(UserModel user1);
}
