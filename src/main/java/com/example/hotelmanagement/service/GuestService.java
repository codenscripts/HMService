package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestService {


    Guest registerGuest(String firstName, String lastName, String email);
    List<Guest> getAllGuests();

    List<Guest> findGuestByFirstName(String firstName);

    Optional<Guest> findGuestById(Long id);
    Optional<Guest> findGuestByEmail(String email);
    List<Guest> findGuestsByFirstName(String firstName);

    Optional<Guest> updateGuestDetails(Long guestId, String newFirstName, String newLastName, String newEmail);

    void deleteGuestById(Long id);

    Optional<Guest> updateGuest(Long id, String firstName, String lastName, String email);
}
