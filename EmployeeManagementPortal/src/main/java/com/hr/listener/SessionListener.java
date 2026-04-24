package com.hr.listener;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static final AtomicInteger activeSessions = new AtomicInteger(0);
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // called when session created
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int now = activeSessions.incrementAndGet();
        System.out.println("[SessionListener] sessionCreated id=" + se.getSession().getId()
            + " time=" + LocalDateTime.now().format(TS) + " active=" + now);
    }

    // called when session destroyed (timeout or invalidation)
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int now = activeSessions.decrementAndGet();
        System.out.println("[SessionListener] sessionDestroyed id=" + se.getSession().getId()
            + " time=" + LocalDateTime.now().format(TS) + " active=" + now);

        // If username attribute present, log user logout event to console (or store in DB later)
        Object u = se.getSession().getAttribute("username");
        if (u != null) {
            System.out.println("[SessionListener] user logged out/timeout username=" + u + " sessionId=" + se.getSession().getId());
        }
    }

    // listen to attribute changes — we'll use this to detect login events
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("username".equals(event.getName())) {
            String username = String.valueOf(event.getValue());
            System.out.println("[SessionListener] user logged in username=" + username
                + " sessionId=" + event.getSession().getId()
                + " at=" + LocalDateTime.now().format(TS));
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        // optional: handle username removal (explicit logout)
        if ("username".equals(event.getName())) {
            String username = String.valueOf(event.getValue());
            System.out.println("[SessionListener] username attribute removed username=" + username
                + " sessionId=" + event.getSession().getId()
                + " at=" + LocalDateTime.now().format(TS));
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // when attribute replaced (e.g., role updated), we can log it
        if ("username".equals(event.getName())) {
            String username = String.valueOf(event.getSession().getAttribute("username"));
            System.out.println("[SessionListener] username attribute replaced username=" + username
                + " sessionId=" + event.getSession().getId()
                + " at=" + LocalDateTime.now().format(TS));
        }
    }

    // simple helper to expose active session count
    public static int getActiveSessionCount() {
        return activeSessions.get();
    }
}
