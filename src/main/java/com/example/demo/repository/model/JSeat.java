package com.example.demo.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
public class JSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private JRoom room;

    @ManyToMany(mappedBy = "seats")
    private List<JReservation> reservations = new ArrayList<>();
}