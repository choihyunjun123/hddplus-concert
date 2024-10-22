package com.example.hddplusconcert.application.port.out;

// 대기열 관리
public interface QueueManager {
    // 유저를 대기열에 추가
    void enqueue(String id);

    // 유저를 대기열에서 제거
    void dequeue(String id);

    // 유저의 대기열 조회
    Long getPosition(String id);

    // 대기열에서 다음 유저를 확인
    String peekNextUser();
}
