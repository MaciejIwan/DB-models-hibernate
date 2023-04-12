package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.entities.Order;
import com.github.maciejiwan.investmens_tracking.entities.Part;
import com.github.maciejiwan.investmens_tracking.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByLineItems_Part(Part part1);

    List<Order> findByBuyer(UserModel user1);
}
