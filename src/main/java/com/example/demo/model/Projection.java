package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projections")
@Getter
@Setter
@NoArgsConstructor
public class Projection {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Instant datetime;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal seatPrice;

  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  @ManyToOne
  @JoinColumn(name = "movie_id", nullable = false)
  private Movie movie;

  @OneToMany(mappedBy = "projection")
  private List<Reservation> reservations = new ArrayList<>();
}
