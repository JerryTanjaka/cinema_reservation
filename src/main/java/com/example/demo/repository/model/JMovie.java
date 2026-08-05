package com.example.demo.repository.model;

import com.example.demo.model.enums.Genre;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class JMovie {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
  @Enumerated(EnumType.STRING)
  private List<Genre> genres = new ArrayList<>();

  @Column(length = 2000)
  private String description;

  @Column(nullable = false)
  private Duration duration;

  @OneToMany(mappedBy = "movie")
  private List<JProjection> projections = new ArrayList<>();
}
