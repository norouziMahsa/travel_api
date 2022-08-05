package com.afkl.travel.exercise.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Translation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Translation implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private Location location;

    @Column(name = "language")
    private String language;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
