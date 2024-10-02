package net.raubuc.objects.reservation.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private final int id;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final int roomId;
    private final int roomNumber;
    private final int guestId;
    private final String guestName;

    public ReservationDTO(int id, LocalDateTime from, LocalDateTime to, int roomId, int roomNumber, int guestId, String guestName) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.guestId = guestId;
        this.guestName = guestName;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }
}
