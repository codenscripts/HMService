package com.example.hotelmanagement.service;

import com.example.hotelmanagement.exception.error.ResourceNotFoundException;
import com.example.hotelmanagement.model.Guest;
import com.example.hotelmanagement.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository) {this.guestRepository = guestRepository;}

    @Override
    @Transactional
    public Guest registerGuest(String firstName, String lastName, String email) {
        if (guestRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email '" + email + "' is already registered to another guest.");
        }
        Guest newGuest = new Guest(firstName, lastName, email);
        return guestRepository.save(newGuest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Guest> getAllGuests(){return guestRepository.findAll();}

    @Override
    @Transactional(readOnly = true)
    public Optional<Guest> findGuestByEmail(String email) {return guestRepository.findByEmail(email);}

    @Override
    public List<Guest> findGuestsByFirstName(String firstName) {
        return List.of();
    }

    @Override
    public Optional<Guest> updateGuestDetails(Long guestId, String newFirstName, String newLastName, String newEmail) {
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Guest> findGuestByFirstName(String firstName) { return guestRepository.findByFirstName(firstName);}

    @Override
    @Transactional(readOnly = true)
    public Optional<Guest> findGuestById(Long id) {return guestRepository.findById(id);}

    @Override
    @Transactional
    public Optional<Guest> updateGuest(Long id, String newFirstName, String newLastName, String newEmail) { // Changed id to Long, params renamed for clarity
        Optional<Guest> guestOptional = guestRepository.findById(id); // Find by ID to update
        if (guestOptional.isPresent()) {
            Guest guestToUpdate = guestOptional.get();

            if (newEmail != null && !newEmail.equalsIgnoreCase(guestToUpdate.getEmail())) {
                Optional<Guest> existingGuestWithNewEmail = guestRepository.findByEmail(newEmail);
                if (existingGuestWithNewEmail.isPresent() && !existingGuestWithNewEmail.get().getId().equals(guestToUpdate.getId())) {
                    throw new IllegalArgumentException("Email '" + newEmail + "' is already registered to another guest.");
                }
                guestToUpdate.setEmail(newEmail);
            }
            if (newFirstName != null) {
                guestToUpdate.setFirstName(newFirstName);
            }
            if (newLastName != null) {
                guestToUpdate.setLastName(newLastName);
            }
            return Optional.of(guestRepository.save(guestToUpdate));
        } else {
            return Optional.empty();
        }
    }


     @Override
     @Transactional
     public void deleteGuestById(Long id) {
         if (!guestRepository.existsById(id)) {
             throw new ResourceNotFoundException("Guest not found with id: " + id);
         }
         guestRepository.deleteById(id);
     }

}
