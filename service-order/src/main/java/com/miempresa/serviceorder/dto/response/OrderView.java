package com.miempresa.serviceorder.dto.response;

import lombok.*;

import java.util.Date;

public interface OrderView {
    Long getId();
    Long getState();
    Long getUserId();
    Double getTotalPrice();
    Date getCreatedAt();
    Date getUpdatedAt();
}
