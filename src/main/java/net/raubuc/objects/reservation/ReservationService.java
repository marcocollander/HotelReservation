package net.raubuc.objects.reservation;

import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.Guest;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.reservation.dto.ReservationDTO;
import net.raubuc.objects.room.Room;
import net.raubuc.objects.room.RoomService;
import net.raubuc.utils.Properties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private final ReservationRepository repository = ObjectPool.getReservationRepository();
    private static final ReservationService instance = new ReservationService();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        return instance;
    }


    public Reservation createNewReservation(LocalDate from, LocalDate to, int roomId, int guestId) {
        //TODO: handle null room
        Room room = this.roomService.getRoomById(roomId);
        //TODO: handle null guest
        Guest guest = this.guestService.getGuestById(guestId);
        LocalDateTime fromWithTime = from.atTime(Properties.HOTEL_NIGHT_START_HOUR, Properties.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(Properties.HOTEL_NIGHT_END_HOUR, Properties.HOTEL_NIGHT_END_MINUTE);

        return repository.createNewReservation(room, guest, fromWithTime, toWithTime);
    }

    public void readAllReservations() {
        repository.readReservations();
    }

    public void saveAllReservations() {
        repository.saveReservations();
    }

    public List<ReservationDTO> getReservationsAsDTO() {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservation> reservations = repository.getReservations();
        for (Reservation reservation : reservations) {
            ReservationDTO dto = reservation.getAsDTO();
            result.add(dto);
        }
        return result;
    }

    public void removeReservation(int reservationId) {
        this.repository.remove(reservationId);
    }
}
