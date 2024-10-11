package com.example.mesresa.controller;

import com.example.mesresa.entity.Fly;
import com.example.mesresa.entity.Ticket;
import com.example.mesresa.entity.User;
import com.example.mesresa.repository.FlyRepository;
import com.example.mesresa.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.mesresa.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlyRepository flyRepository;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fly/{flyId}")
    public ResponseEntity<List<Ticket>> getTicketsByFlyId(@PathVariable Long flyId) {
        Fly fly = flyRepository.findById(flyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fly not found"));
        return ResponseEntity.ok(fly.getTickets());
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUserByTicketId(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ticket -> ResponseEntity.ok(ticket.getPassenger()))
                .orElse(ResponseEntity.notFound().build());
    }

}
