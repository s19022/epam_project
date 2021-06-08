package com.example.InspectionBoard.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class ServiceUtility {
    private static final int MAX_MARK = 12;
    private static final int MIN_MARK = 1;

    private static final Logger LOGGER = LogManager.getLogger(ServiceUtility.class.getName());

    /**
     *
     * @param password String to be hashed
     * @return hashed input using SHA1
     */
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

    public static boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }


    public static boolean isMarkValid(int mark){
        return mark >= MIN_MARK && mark <= MAX_MARK;
    }
}
