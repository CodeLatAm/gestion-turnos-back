package com.getion.turnos.testutil;


import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.request.RegisterRequest;

public class TestUtils {

    public static RegisterRequest creteUser(){
        return RegisterRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .password("12345678")
                .title("DR")
                .country("Agentina")
                .username("abel@gmail.com")
                .build();
    }
    public static ProfileRequest createProfileRequest() {
        return ProfileRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .city("BS AS")
                .phone("1234567890")
                .domicile("Garin")
                .presentation("DR ONCOLOGO")
                .mat_prov("12A12")
                .specialty("ONCOLOGO")
                .province("BS AS")
                .whatsapp("123313121")
                .mat_nac("12A12")
                .build();
    }
}
