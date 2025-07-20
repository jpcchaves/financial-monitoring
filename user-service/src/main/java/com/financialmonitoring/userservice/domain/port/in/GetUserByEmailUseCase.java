package com.financialmonitoring.userservice.domain.port.in;

import com.financialmonitoring.userservice.adapter.out.entity.User;

public interface GetUserByEmailUseCase {
    User getUserByEmail(String email);
}
