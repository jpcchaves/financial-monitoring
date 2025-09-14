package com.financialmonitoring.userservice.port.input;

import com.financialmonitoring.userservice.infra.model.User;

public interface GetUserByEmailUseCase {
    User getUserByEmail(String email);
}
