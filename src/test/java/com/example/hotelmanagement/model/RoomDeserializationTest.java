package com.example.hotelmanagement.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest
public class RoomDeserializationTest {

    @Test
    public void testRoomDeserialization() throws Exception {
        // Create JSON string representing a room
        String roomJson = "{\"roomNumber\":\"101\",\"roomType\":\"SINGLE\"}";
        
        // Create ObjectMapper (Jackson's main class for JSON processing)
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Deserialize JSON to Room object
        Room room = objectMapper.readValue(roomJson, Room.class);
        
        // Verify deserialization worked correctly
        assertNotNull(room, "Room should not be null after deserialization");
        assertEquals("101", room.getRoomNumber(), "Room number should match");
        assertEquals(RoomType.SINGLE, room.getRoomType(), "Room type should match");
    }
}