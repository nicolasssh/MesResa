package com.example.mesresa.controller;

import com.example.mesresa.entity.Fly;
import com.example.mesresa.entity.Ticket;
import com.example.mesresa.entity.User;
import com.example.mesresa.repository.FlyRepository;
import com.example.mesresa.repository.TicketRepository;
import com.example.mesresa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mesresa.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/flies")
public class FlyController {

    @Autowired
    private FlyRepository flyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Fly> getAllFlies() {
        return flyRepository.findAll();
    }

    @PostMapping
    public Fly createFly(@RequestBody Fly fly) {
        return flyRepository.save(fly);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fly> getFlyById(@PathVariable Long id) {
        return flyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{flyId}/book/{userId}")
    public ResponseEntity<?> bookFlyForUser(@PathVariable Long flyId, @PathVariable Long userId, @RequestBody Ticket ticketDetails) {
        Fly fly = flyRepository.findById(flyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fly not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (fly.getPassengers().size() >= fly.getNumberSeats()) {
            return ResponseEntity.badRequest().body("No seats available on this flight");
        }

        Ticket ticket = new Ticket();
        ticket.setFly(fly);
        ticket.setPrice(ticketDetails.getPrice());
        ticket.setSeat(ticketDetails.getSeat());
        ticket.setPassenger(user);

        ticket = ticketRepository.save(ticket);

        fly.getTickets().add(ticket);
        flyRepository.save(fly);

        return ResponseEntity.ok().body("Flight booked successfully");
    }
}
