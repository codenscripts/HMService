package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomStatus;
import com.example.hotelmanagement.model.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room addRoom(String roomNumber, RoomType type);
    List<Room> getAllRooms();
    Optional<Room> findRoomByNumber(String roomNumber);
    Optional<Room> updateRoomStatus(String roomNumber, RoomStatus newStatus);
}
