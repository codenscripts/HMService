package com.example.hotelmanagement.DTO;

import com.example.hotelmanagement.model.RoomStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateRoomStatusRequest {

    @NotNull(message = "Room status cannot be empty")
    private RoomStatus newStatus;

    public RoomStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(RoomStatus newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public String toString() {
        return "UpdateRoomStatusRequest{" +
                "newStatus=" + newStatus +
                '}';
    }
}
