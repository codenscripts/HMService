package com.example.hotelmanagement.repository;

import com.example.hotelmanagement.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByEmail(String email);

    List<Guest> findByFirstName(String firstName);
    List<Guest> findByLastName(String lastName);
}
