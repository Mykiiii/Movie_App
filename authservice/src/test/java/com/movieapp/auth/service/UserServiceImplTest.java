package com.movieapp.auth.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.movieapp.auth.model.User;
import com.movieapp.auth.util.exception.UserNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testFindByUserIdAndPassword2() throws UserNotFoundException, RestClientException {
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(null);
        assertNull(userServiceImpl.findByUserIdAndPassword(123, "iloveyou"));
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
    }

    @Test
    void testFindByUserIdAndPassword3() throws UserNotFoundException, RestClientException {
        User user = new User(123, "iloveyou", "iloveyou", "4105551212", "jane.doe@example.org", true);

        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        assertSame(user, userServiceImpl.findByUserIdAndPassword(123, "iloveyou"));
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
    }

    @Test
    void testFindByUserIdAndPassword4() throws UserNotFoundException, RestClientException {
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any()))
                .thenReturn(new User(123, "Password", "iloveyou", "4105551212", "jane.doe@example.org", true));
        assertNull(userServiceImpl.findByUserIdAndPassword(123, "iloveyou"));
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
    }
}

