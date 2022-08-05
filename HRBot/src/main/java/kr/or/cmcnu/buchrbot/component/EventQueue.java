package kr.or.cmcnu.buchrbot.component;

import org.springframework.stereotype.Component;

import kr.or.cmcnu.buchrbot.pojo.event.TeamupEventChat;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thisno on 2016-04-12.
 */

@Component
public class EventQueue<T> {
    final Queue<T> queue = new ConcurrentLinkedQueue<T>();

    public void offer(T e) {
        if (e == null) {
            return;
        }
        queue.offer(e);
    }

    public T poll() {
        return queue.poll();
    }
}
