package com.mftech.monolith_ecommerce.dto;

import com.mftech.monolith_ecommerce.model.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private List<AddressDTO> addresses;
}
