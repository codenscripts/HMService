package com.example.hotelmanagement.controllerAPI;

import com.example.hotelmanagement.DTO.CreateRoomRequest;
import com.example.hotelmanagement.DTO.UpdateRoomStatusRequest;
import com.example.hotelmanagement.model.Room;
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
@RequestMapping("api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> getRoomByNumber(@PathVariable String roomNumber) {
        Optional<Room> room = roomService.findRoomByNumber(roomNumber);

//        if (room.isPresent()) {
//            return ResponseEntity.ok(room.get());
//        }
//        return ResponseEntity.notFound().build();
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping()
    public ResponseEntity<Room> addRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest) {

        try{
            Room newRoom = roomService.addRoom(createRoomRequest.getRoomNumber(), createRoomRequest.getRoomType());
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PutMapping("/{roomNumber}/status")
    public ResponseEntity<Room> updateRoomStatus(@PathVariable String roomNumber, @Valid @RequestBody UpdateRoomStatusRequest updateRequest) {
        Optional<Room> updatedRoom = roomService.updateRoomStatus(roomNumber, updateRequest.getNewStatus());

        return updatedRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            }
}
