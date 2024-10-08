package net.raubuc.objects.reservation;

import net.raubuc.exceptions.PersistenceToFileException;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.Guest;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.room.Room;
import net.raubuc.objects.room.RoomService;
import net.raubuc.utils.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    List<Reservation> reservations = new ArrayList<>();
    RoomService roomService = ObjectPool.getRoomService();
    GuestService guestService = ObjectPool.getGuestService();

    private static final ReservationRepository instance = new ReservationRepository();

    private ReservationRepository() {
    }

    public static ReservationRepository getInstance() {
        return instance;
    }


    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation res = new Reservation(findNewId(), room, guest, from, to);
        reservations.add(res);
        return res;
    }

    private int findNewId() {
        int max = 0;

        for (Reservation reservation : reservations) {
            if (reservation.getId() > max) {
                max = reservation.getId();
            }
        }
        return max + 1;
    }

    public void readReservations() {
        String name = "reservations.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }
        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] reservationsAsString = data.split(System.lineSeparator());

            for (String reservationAsString : reservationsAsString) {

                String[] reservationData = reservationAsString.split(",");
                if (reservationData.length != 5) {
                    return;
                }
                int id = Integer.parseInt(reservationData[0]);
                int roomId = Integer.parseInt(reservationData[1]);
                int guestId = Integer.parseInt(reservationData[2]);
                String fromAsString = reservationData[3];
                String toAsString = reservationData[4];
                //TODO handle null guest/room

                addExistingReservation(id, this.roomService.getRoomById(roomId), this.guestService.getGuestById(guestId), LocalDateTime.parse(fromAsString), LocalDateTime.parse(toAsString));
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "guests data");
        }
    }

    private void addExistingReservation(int id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation res = new Reservation(id, room, guest, from, to);

        reservations.add(res);
    }

    public void saveReservations() {
        String name = "reservations.csv";
        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);
        StringBuilder sb = new StringBuilder();

        for (Reservation reservation : reservations) {
            sb.append(reservation.toCSV());
        }

        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "write", "reservation data");
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void remove(int id) {
        int reservationToBeRemovedIndex = -1;
        for (int i = 0; i < this.reservations.size(); i++) {
            if (this.reservations.get(i).getId() == id) {
                reservationToBeRemovedIndex = i;
                break;
            }
        }
        if (reservationToBeRemovedIndex > -1) {
            this.reservations.remove(reservationToBeRemovedIndex);
        }
    }


}
