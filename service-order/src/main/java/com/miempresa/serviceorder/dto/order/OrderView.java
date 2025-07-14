package com.miempresa.serviceorder.dto.order;

import java.util.Date;

public interface OrderView {
    Long getId();
    Long getState();
    Long getUserId();
    Double getTotalPrice();
    Date getCreatedAt();
    Date getUpdatedAt();
}
