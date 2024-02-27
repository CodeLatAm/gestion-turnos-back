package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTotalGendersResponse {
    Integer totalMale;
    Integer totalFemale;
    Integer totalTransgender;
}
