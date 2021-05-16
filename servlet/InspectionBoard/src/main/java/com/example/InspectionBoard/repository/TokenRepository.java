/*
package com.example.InspectionBoard.repository;

import com.example.InspectionBoard.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TokenRepository {
    private static final Logger LOGGER = LogManager.getLogger(TokenRepository.class.getName());
    private static final int SLEEP_TIME = 5_000 * 60;
    private static final Map<Token, Account> TOKENS = new HashMap<>();

    static {
        deleteExpiredToken();
    }

    public static UUID add(Account account){
        if (TOKENS.containsValue(account)){
            deleteDuplicateToken(account);
        }
        Token t = Token.getInstance();
        TOKENS.put(t, account);
        System.out.println(TOKENS.keySet());
        return t.getValue();
    }

    public static Optional<Account> getAccount(UUID token){
        return TOKENS.entrySet().stream()
                .filter(e -> e.getKey().getValue().equals(token))
                .filter(e -> e.getKey().isAlive())
                .map(Map.Entry::getValue).findFirst();
    }

    public static boolean contains(UUID token){
        Token t = new Token(token);
        return TOKENS.containsKey(t);
    }

    private static void deleteDuplicateToken(Account account){
        TOKENS.values().remove(account);
    }

    private static void deleteExpiredToken(){
        new Thread(() -> {
            while (true){
                TOKENS.entrySet().removeIf(e -> !e.getKey().isAlive());
                try {
                    Thread.sleep(SLEEP_TIME);
                }catch (InterruptedException ex){
                    LOGGER.error("while deleting expired tokens", ex);
                }
            }
        }).start();
    }
}
*/
