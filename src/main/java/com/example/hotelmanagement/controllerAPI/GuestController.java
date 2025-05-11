package com.example.hotelmanagement.controllerAPI;

import com.example.hotelmanagement.DTO.CreateGuestRequest;
import com.example.hotelmanagement.DTO.CreateRoomRequest;
import com.example.hotelmanagement.DTO.UpdateRoomStatusRequest;
import com.example.hotelmanagement.model.Guest;
import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.service.GuestService;
import com.example.hotelmanagement.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/by-firstname/{firstName}")
    public ResponseEntity<List<Guest>> getGuestsByFirstName(@PathVariable String firstName) {
        List<Guest> guests = guestService.findGuestsByFirstName(firstName);

        if (guests.isEmpty()) {
            return ResponseEntity.ok(guests);

        } else {
            return ResponseEntity.ok(guests);
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable String email) {
        Optional<Guest> guest = guestService.findGuestByEmail(email);

        return guest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Guest> registerGuest(@Valid @RequestBody CreateGuestRequest createGuestRequest) {
        try{
            Guest newGuest = guestService.registerGuest(createGuestRequest.getFirstName(), createGuestRequest.getLastName(), createGuestRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(newGuest);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}
