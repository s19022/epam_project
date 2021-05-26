package com.example.InspectionBoard.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class ServiceUtility {
    private static final Logger LOGGER = LogManager.getLogger(ServiceUtility.class.getName());
    public static String hash(String password) {
        //stole from stackoverflow, seems to work correctly
        try {
            byte[] unencodedPassword = password.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.reset();
            md.update(unencodedPassword);
            byte[] encodedPassword = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : encodedPassword) {
                if (((int) b & 0xff) < 0x10) {
                    sb.append("0");
                }
                sb.append(Long.toString((int) b & 0xff, 16));
            }
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error(e);
            return password;
        }
    }
    public static String decode(String toDecode){
        byte[] decoded = Base64.getDecoder().decode(toDecode.getBytes(StandardCharsets.UTF_8));
        return new String(decoded);
    }
}
