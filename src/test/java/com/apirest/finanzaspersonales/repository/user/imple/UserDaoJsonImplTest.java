package com.apirest.finanzaspersonales.repository.user.imple;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class UserDaoJsonImplTest extends UserDaoTestBase{

    @InjectMocks
    private UserDaoJsonImpl userDaoJsonImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDao = userDaoJsonImpl;
        userDaoJsonImpl.reset();
    }

}