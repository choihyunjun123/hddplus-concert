package com.example.hddplusconcert.adapter.out.queue;

import com.example.hddplusconcert.application.port.out.QueueManager;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class InMemoryQueueManager implements QueueManager {

    // 유저 Id를 저장하는 큐
    private final Queue<String> queue = new LinkedList<>();

    @Override
    public void enqueue(String userId) {
        queue.add(userId);
    }

    @Override
    public void dequeue(String userId) {
        queue.remove(userId);
    }

    @Override
    public Long getPosition(String userId) {
        long position = 1;
        for (String id : queue) {
            if (id.equals(userId)) {
                return position;
            }
            position++;
        }
        return -1L;
    }

    @Override
    public String peekNextUser() {
        return "";
    }
}
