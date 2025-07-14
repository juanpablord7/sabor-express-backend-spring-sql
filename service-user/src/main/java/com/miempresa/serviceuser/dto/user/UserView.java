package com.miempresa.serviceuser.dto.user;

import java.util.Date;

public interface UserView {
    Long getId();

    String getUsername();

    String getFullname();

    String getEmail();

    Long getRole();

    Date getCreatedAt();

    Date getUpdatedAt();
}
