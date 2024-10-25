package com.example.hddplusconcert.common.dto.auth;

public record QueuePositionResponse(String userId, Long position) {

    public static QueuePositionResponse fromDomainModel(String userId, Long position) {
        return new QueuePositionResponse(
                userId,
                position
        );
    }
}
