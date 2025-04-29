package com.example.hotelmanagement.service;

import com.example.hotelmanagement.model.Room;
import com.example.hotelmanagement.model.RoomStatus;
import com.example.hotelmanagement.model.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.testng.Assert.*;
import org.testng.annotations.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RoomServiceImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private RoomService roomService;

    @BeforeMethod
    public void setUp() {
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testAddAndRetireveRoom() {
        String roomNumber = "123";
        RoomType type = RoomType.SINGLE;

        Room addedRoom = roomService.addRoom(roomNumber, type);
        Optional<Room> retrievedRoomOpt = roomService.findRoomByNumber(roomNumber);
        List<Room> allRooms = roomService.getAllRooms();

        assertNotNull(addedRoom, "The added room should not be null");
        System.out.println("Added room: " + addedRoom.getRoomNumber());
        System.out.println("Added room status: " + addedRoom.getStatus());
        System.out.println("Added room type: " + addedRoom.getRoomType());
        assertEquals(addedRoom.getRoomNumber(), roomNumber);
        assertEquals(addedRoom.getRoomType(), type);
        assertEquals(addedRoom.getStatus(), RoomStatus.AVAILABLE, "The room status should be AVAILABLE");
        assertTrue(retrievedRoomOpt.isPresent(), "Should return room number");

        Room retrievedRoom = retrievedRoomOpt.get();
        assertEquals(retrievedRoom.getRoomNumber(), roomNumber);

        assertFalse(allRooms.isEmpty(), "Room list should not be empty");
        assertTrue(allRooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber)), "Added room should be in the list of All Rooms");

    }


    @Test
    public void testUpdateRoomStatus() {
        String roomNumber = "102";
        roomService.addRoom(roomNumber, RoomType.DOUBLE);

        Optional<Room> updatedRoomOpt = roomService.updateRoomStatus(roomNumber, RoomStatus.CLEANING);

        assertTrue(updatedRoomOpt.isPresent(), "Updated rooms should be present");
        assertEquals(updatedRoomOpt.get().getStatus(), RoomStatus.CLEANING, "Status should be updated to CLEANING");


        Optional<Room> retrievedRoomOpt = roomService.findRoomByNumber(roomNumber);
        assertTrue(retrievedRoomOpt.isPresent(), "Should return room number");
        assertEquals(retrievedRoomOpt.get().getStatus(), RoomStatus.CLEANING, "Status should be updated");
    }

    @Test
    void testFindNonExistentRoom() {
        Optional<Room> roomOpt = roomService.findRoomByNumber("101");

        assertFalse(roomOpt.isPresent(), "Should not return a non existent room");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddDuplicateRoom() {
        String roomNumber = "T103";
        roomService.addRoom(roomNumber, RoomType.SUITE);
        roomService.addRoom(roomNumber, RoomType.SUITE);
    }

}
