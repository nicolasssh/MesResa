package com.example.mesresa.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private String seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fly_id")
    private Fly fly;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private User passenger;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Fly getFly() {
        return fly;
    }

    public void setFly(Fly fly) {
        this.fly = fly;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }
}
