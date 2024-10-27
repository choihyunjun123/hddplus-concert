package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.SeatUseCase;
import com.example.hddplusconcert.application.port.out.QueueManager;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.Seat;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatService implements SeatUseCase {

    private final SeatRepository seatRepository;
    private final QueueManager queueManager;
    private final UserRepository userRepository;

    public SeatService(SeatRepository seatRepository, QueueManager queueManager, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.queueManager = queueManager;
        this.userRepository = userRepository;
    }

    // 좌석을 임시로 예약
    @Override
    @Transactional
    public Long holdSeat(String userId, Long seatNumber, Long concertId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!queueManager.isUserFirstInQueue(user.getId().toString())) {
            return queueManager.getPosition(user.getId().toString());
        }

        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));

        if (!seat.isAvailable()) {
            throw new CustomException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        seat.hold(userId, LocalDateTime.now().plusMinutes(5));

        seatRepository.save(seat);

        queueManager.dequeue(user.getId().toString());

        return 1L;
    }

    @Override
    @Transactional
    public Seat reserveSeat(String userId, Long seatNumber, Long concertId) {
        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));

        if (!seat.isHeldBy(userId)) {
            throw new CustomException(ErrorCode.SEAT_NOT_HELD_BY_USER);
        }

        seat.reserve(userId);

        seatRepository.save(seat);

        return seat;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredHolds() {
        List<Seat> expiredSeats = seatRepository.findAllByStatusAndHeldUntilBefore(Seat.SeatStatus.HELD, LocalDateTime.now());

        expiredSeats.forEach(seat -> {
            seat.releaseHold();
            seatRepository.save(seat);
        });
    }
}
