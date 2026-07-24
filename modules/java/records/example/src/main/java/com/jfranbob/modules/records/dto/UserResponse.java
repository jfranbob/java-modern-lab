package com.jfranbob.modules.records.dto;

import java.util.List;

public record UserResponse(Long id, String name, String email, List<String> roles, boolean active) {

    public UserResponse {
        roles = roles == null ? List.of() : List.copyOf(roles);
    }
}
