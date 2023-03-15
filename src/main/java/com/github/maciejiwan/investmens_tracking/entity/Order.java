package com.github.maciejiwan.investmens_tracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private UserModel buyer;

    private Date date;

    private boolean paid;

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    List<LineItem> lineItems;
}
