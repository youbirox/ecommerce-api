package com.shop.backend.dto.response;

import com.shop.backend.entity.Role;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String email;

    private Role role;

}
