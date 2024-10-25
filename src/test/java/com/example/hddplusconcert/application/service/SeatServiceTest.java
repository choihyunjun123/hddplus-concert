package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.out.QueueManager;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.Seat;
import com.example.hddplusconcert.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SeatServiceTest {

    @InjectMocks
    private SeatService seatService;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QueueManager queueManager;

    private final String userId = "testUserId";
    private final Long seatNumber = 1L;
    private final Long concertId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유저가 없으면 예외를 던진다")
    void holdSeat_UserNotFound() {
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.holdSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("좌석이 없으면 예외를 던진다")
    void holdSeat_SeatNotFound() {
        User user = User.createNewUser(userId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.holdSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.SEAT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("이미 예약된 좌석이면 예외를 던진다")
    void holdSeat_SeatNotAvailable() {
        User user = User.createNewUser(userId);
        Seat seat = new Seat(seatNumber, concertId);
        seat.hold("anotherUserId", LocalDateTime.now());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.holdSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.SEAT_NOT_AVAILABLE, exception.getErrorCode());
    }

    @Test
    @DisplayName("대기열에서 첫 번째 유저가 아니면 대기열 순서를 반환한다")
    void holdSeat_UserNotFirstInQueue() {
        User user = User.createNewUser(userId);
        Seat seat = new Seat(seatNumber, concertId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));
        when(queueManager.isUserFirstInQueue(user.getId().toString())).thenReturn(false);
        when(queueManager.getPosition(user.getId().toString())).thenReturn(5L);

        Long position = seatService.holdSeat(userId, seatNumber, concertId);

        assertEquals(5L, position);
    }

    @Test
    @DisplayName("대기열에서 첫 번째 유저일 때 좌석 예약이 성공한다")
    void holdSeat_Success() {
        User user = User.createNewUser(userId);
        Seat seat = new Seat(seatNumber, concertId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));
        when(queueManager.isUserFirstInQueue(user.getId().toString())).thenReturn(true);

        Long result = seatService.holdSeat(userId, seatNumber, concertId);

        assertEquals(result, 1L);
        verify(seatRepository).save(seat);
        verify(queueManager).dequeue(user.getId().toString());
    }

    @Test
    @DisplayName("좌석 예약 - 유저가 없으면 예외 발생")
    void reserveSeat_UserNotFound() {
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.reserveSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("좌석 예약 - 좌석이 없으면 예외를 던진다")
    void reserveSeat_SeatNotFound() {
        User user = User.createNewUser(userId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.reserveSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.SEAT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("좌석 예약 - 임시 예약이 아닌 좌석이면 예외를 던진다")
    void reserveSeat_SeatNotHeldByUser() {
        User user = User.createNewUser(userId);
        Seat seat = new Seat(seatNumber, concertId);
        seat.hold("anotherUserId", LocalDateTime.now());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));

        CustomException exception = assertThrows(CustomException.class,
                () -> seatService.reserveSeat(userId, seatNumber, concertId));

        assertEquals(ErrorCode.SEAT_NOT_HELD_BY_USER, exception.getErrorCode());
    }

    @Test
    @DisplayName("좌석 예약 성공")
    void reserveSeat_Success() {
        User user = User.createNewUser(userId);
        Seat seat = new Seat(seatNumber, concertId);
        seat.hold(userId, LocalDateTime.now());

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(user));
        when(seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)).thenReturn(Optional.of(seat));

        Seat result = seatService.reserveSeat(userId, seatNumber, concertId);

        assertEquals(result, seat);
        verify(seatRepository).save(seat);
    }
}
