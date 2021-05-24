package com.example.InspectionBoard.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.security.MessageDigest;

public class HashUtility {
    private static final Logger LOGGER = LogManager.getLogger(HashUtility.class.getName());

    public static String encodePassword(String password) {
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
}
