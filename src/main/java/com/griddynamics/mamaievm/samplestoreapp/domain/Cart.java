package com.griddynamics.mamaievm.samplestoreapp.domain;

import com.griddynamics.mamaievm.samplestoreapp.entity.Order;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
//to avoid circular reference on hashCode calculation
//@EqualsAndHashCode(exclude = {"products"})
@RequiredArgsConstructor
public class Cart implements Serializable {
    
    private final HttpSession httpSession;
    List<CartProduct> products = new ArrayList<>();
    Order order = new Order();
    String sessionId;
    
    public void setSessionIdString() {
        sessionId = httpSession.getId();
    }
    
    @PreDestroy
//    public void clearCart(Session session) {
//        log.info("###Clearing cart...for session" + session.getCookie());
//    }
    public void clearCart() {
        log.info("###Clearing cart...for session" + sessionId);
    }
}
