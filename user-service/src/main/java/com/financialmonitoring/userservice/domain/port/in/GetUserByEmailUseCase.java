package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.infra.model.User;

public interface GetUserByEmailUseCase {
    User getUserByEmail(String email);
}
