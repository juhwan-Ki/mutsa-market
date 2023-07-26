package com.mutsa.market.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String passwordCheck;
}
