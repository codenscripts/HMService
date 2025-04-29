package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomStatus;
import com.example.hotelmanagement.model.RoomType;

import com.example.hotelmanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Override
    public Room addRoom(String roomNumber, RoomType type) {
        if (roomRepository.findByRoomNumber(roomNumber).isPresent()) {
            throw new IllegalArgumentException("Room number "+  roomNumber +" already exists");
        }
        Room newRoom = new Room(roomNumber, type, RoomStatus.AVAILABLE);
        return roomRepository.save(newRoom);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public Optional<Room> updateRoomStatus(String roomNumber, RoomStatus newStatus) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumber(roomNumber);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setStatus(newStatus);
            roomRepository.save(room);
            return Optional.of(room);
        } else {
            return Optional.empty();
        }
    }
}
