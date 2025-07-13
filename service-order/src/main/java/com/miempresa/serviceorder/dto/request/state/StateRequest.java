package com.miempresa.serviceorder.dto.request.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StateRequest {
    private Long state;
}
