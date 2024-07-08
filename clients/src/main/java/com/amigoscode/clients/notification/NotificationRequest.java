package com.amigoscode.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private Integer id;
    private Integer toCustomerId;
    private String toCustomerEmail;
    private String message;

    public NotificationRequest(Integer id, String email, String format) {
    }
}
