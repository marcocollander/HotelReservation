package net.raubuc.objects.reservation;

import net.raubuc.objects.guest.Guest;
import net.raubuc.objects.reservation.dto.ReservationDTO;
import net.raubuc.objects.room.Room;

import java.time.LocalDateTime;

public class Reservation {
    private final int id;
    private final Room room;
    private final Guest guest;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Reservation(int id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    String toCSV() {
        return String.format("%d,%d,%d,%s,%s,%s",
                this.id,
                this.room.getId(),
                this.guest.getId(),
                this.from.toString(),
                this.to.toString(),
                System.lineSeparator());
    }

    public int getId() {
        return id;
    }

    public ReservationDTO getAsDTO() {
        return new ReservationDTO(
                this.id,
                this.from,
                this.to,
                this.room.getId(),
                this.room.getNumber(),
                this.guest.getId(),
                this.guest.getFirstName() + " " + this.guest.getLastName()
        );
    }
}
