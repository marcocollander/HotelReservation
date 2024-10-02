package net.raubuc.objects;

import net.raubuc.objects.guest.GuestRepository;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.reservation.ReservationRepository;
import net.raubuc.objects.reservation.ReservationService;
import net.raubuc.objects.room.RoomRepository;
import net.raubuc.objects.room.RoomService;

public class ObjectPool {

    private ObjectPool() {
    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
        return GuestRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
        return RoomRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }
}