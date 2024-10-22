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
    public void enqueue(String id) {
        queue.add(id);
    }

    @Override
    public void dequeue(String id) {
        queue.remove(id);
    }

    @Override
    public Long getPosition(String id) {
        long position = 1;
        for (String UUID : queue) {
            if (UUID.equals(id)) {
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
