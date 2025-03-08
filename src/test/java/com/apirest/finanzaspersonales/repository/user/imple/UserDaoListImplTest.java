package com.apirest.finanzaspersonales.repository.user.imple;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class UserDaoListImplTest extends UserDaoTestBase{

    @InjectMocks
    private UserDaoJsonImpl userDaoListImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDao = userDaoListImpl;
        userDaoListImpl.reset();
    }

}
