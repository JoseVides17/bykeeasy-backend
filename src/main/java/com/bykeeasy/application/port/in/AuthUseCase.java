package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.User;
import com.bykeeasy.domain.model.UserRole;

public interface AuthUseCase {
    User login(String email, String password);
    User register(String fullName, String email, String password, String phone, UserRole role);
    User getUserById(String userId);
}
