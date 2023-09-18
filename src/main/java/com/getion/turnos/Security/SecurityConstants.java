package com.getion.turnos.Security;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ISSUER = "Tag-Solution";
    public static final String SECRET = "bH0JEkmI05NL/UQyBjER6xkckecLYnLVVg/UbvVumod3E9Nr3SbNxiPMOzFGL+Kd";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
}
