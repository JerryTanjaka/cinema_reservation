package com.example.demo.repository.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
public class JRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String number;

  @Column(nullable = false)
  private int capacity;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JSeat> seats = new ArrayList<>();

  @OneToMany(mappedBy = "room")
  private List<JProjection> projections = new ArrayList<>();

  public void addSeat(JSeat seat) {
    seats.add(seat);
    seat.setRoom(this);
  }
}
