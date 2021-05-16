/*
package com.example.InspectionBoard.repository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Token {
    private static final int MINUTES_TO_LIVE = 15;
    private final UUID value;
    private final LocalDateTime timeToLive;

    private Token(UUID value, LocalDateTime timeToLive) {
        this.value = value;
        this.timeToLive = timeToLive;
    }

    public Token(UUID value){
        this(value, null);
    }

    public boolean isAlive(){
        if (timeToLive == null){
            return false;
        }
        return timeToLive.isBefore(LocalDateTime.now());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return value.equals(token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + ", " + timeToLive;
    }

    public static Token getInstance(){
        return new Token(UUID.randomUUID(), LocalDateTime.now().plusMinutes(MINUTES_TO_LIVE));
    }
}
*/
