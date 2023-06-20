package com.github.maciejiwan.investmens_tracking.entity;

import com.github.maciejiwan.investmens_tracking.enums.Country;
import com.github.maciejiwan.investmens_tracking.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ConferenceParticipant")
@Getter
@Setter
public class ConferenceParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "presenter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Presentation> presentations = new ArrayList<>();

    public void addPresentation(Presentation presentation) {
        presentations.add(presentation);
        presentation.setPresenter(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConferenceParticipant that = (ConferenceParticipant) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}