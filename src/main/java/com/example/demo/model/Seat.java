package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String number;

  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  @ManyToMany(mappedBy = "seats")
  private List<Reservation> reservations = new ArrayList<>();
}
