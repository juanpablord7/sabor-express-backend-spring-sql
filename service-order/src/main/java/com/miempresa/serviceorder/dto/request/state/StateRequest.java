package com.miempresa.serviceorder.dto.request.stateorder;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StateOrderRequest {
    private Long state;
}
