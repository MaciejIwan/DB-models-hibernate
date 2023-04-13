package com.github.maciejiwan.investmens_tracking.entities;

import com.github.maciejiwan.investmens_tracking.enums.TicketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Screening screening;

    @OneToOne
    private UserModel user;

    private TicketType type;
}
