package com.example.demo.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projections")
@Getter
@Setter
@NoArgsConstructor
public class JProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Instant datetime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal seatPrice;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private JRoom room;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private JMovie movie;

    @OneToMany(mappedBy = "projection")
    private List<JReservation> reservations = new ArrayList<>();
}