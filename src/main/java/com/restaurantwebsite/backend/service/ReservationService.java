package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.CreateReservationRequest;
import com.restaurantwebsite.backend.dto.ReservationResponse;
import com.restaurantwebsite.backend.entity.Reservation;
import com.restaurantwebsite.backend.exception.ResourceNotFoundException;
import com.restaurantwebsite.backend.repository.ReservationRepository;
import com.restaurantwebsite.backend.util.BusinessHours;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResponse create(CreateReservationRequest request) {
        if (!BusinessHours.isValidReservationTime(request.getReservationDate(), request.getReservationTime())) {
            throw new IllegalArgumentException(
                    "Selected date/time is outside our reservation hours. Please choose a valid time slot.");
        }

        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.getCustomerName());
        reservation.setPhone(request.getPhone());
        reservation.setReservationDate(request.getReservationDate());
        reservation.setReservationTime(request.getReservationTime());
        reservation.setPartySize(request.getPartySize());
        reservation.setNotes(request.getNotes());

        Reservation saved = reservationRepository.save(reservation);
        return toResponse(saved);
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.findAllByOrderByReservationDateAscReservationTimeAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        reservationRepository.delete(reservation);
    }

    private ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(
                r.getId(),
                r.getCustomerName(),
                r.getPhone(),
                r.getReservationDate(),
                r.getReservationTime(),
                r.getPartySize(),
                r.getNotes(),
                r.getCreatedAt()
        );
    }
}