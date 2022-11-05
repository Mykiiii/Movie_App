package com.movie.userservice.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.movie.userservice.model.User;
import com.movie.userservice.repository.UserRepository;
import com.movie.userservice.util.exception.UserAlreadyExistException;
import com.movie.userservice.util.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testSaveUser() throws UserAlreadyExistException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsAdmin(true);
        user2.setName("Name");
        user2.setPassword("password");
        user2.setPhoneNumber("4105551212");
        user2.setUserId(123);
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.saveUser(user2));
        verify(userRepository).findById((Integer) any());
    }


    @Test
    void testSaveUser2() throws UserAlreadyExistException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        assertSame(user, userServiceImpl.saveUser(user1));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testSaveUser3() throws UserAlreadyExistException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsAdmin(true);
        user2.setName("Name");
        user2.setPassword("password");
        user2.setPhoneNumber("4105551212");
        user2.setUserId(123);
        assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.saveUser(user2));
        verify(userRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#saveUser(User)}
     */
    @Test
    void testSaveUser4() throws UserAlreadyExistException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        assertSame(user, userServiceImpl.saveUser(user1));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Integer) any());
    }


    @Test
    void testGetById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(user, userServiceImpl.getById(1));
        verify(userRepository).findById((Integer) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getById(int)}
     */
    @Test
    void testGetById2() {
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertNull(userServiceImpl.getById(1));
        verify(userRepository).findById((Integer) any());
    }


    @Test
    void testGetById3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertSame(user, userServiceImpl.getById(1));
        verify(userRepository).findById((Integer) any());
    }


    @Test
    void testGetById4() {
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertNull(userServiceImpl.getById(1));
        verify(userRepository).findById((Integer) any());
    }


    @Test
    void testGetAllUser() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUser = userServiceImpl.getAllUser();
        assertSame(userList, actualAllUser);
        assertTrue(actualAllUser.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testGetAllUser2() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUser = userServiceImpl.getAllUser();
        assertSame(userList, actualAllUser);
        assertTrue(actualAllUser.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        when(userRepository.saveAndFlush((User) any())).thenReturn(user1);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsAdmin(true);
        user2.setName("Name");
        user2.setPassword("password");
        user2.setPhoneNumber("4105551212");
        user2.setUserId(123);
        assertSame(user1, userServiceImpl.updateUser(user2));
        verify(userRepository).saveAndFlush((User) any());
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testUpdateUser2() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        when(userRepository.saveAndFlush((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(user1));
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testUpdateUser3() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        when(userRepository.saveAndFlush((User) any())).thenReturn(user1);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsAdmin(true);
        user2.setName("Name");
        user2.setPassword("password");
        user2.setPhoneNumber("4105551212");
        user2.setUserId(123);
        assertSame(user1, userServiceImpl.updateUser(user2));
        verify(userRepository).saveAndFlush((User) any());
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testUpdateUser4() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        when(userRepository.saveAndFlush((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsAdmin(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setPhoneNumber("4105551212");
        user1.setUserId(123);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(user1));
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testDeleteUser() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertTrue(userServiceImpl.deleteUser(1));
        verify(userRepository).findById((Integer) any());
        verify(userRepository).deleteById((Integer) any());
    }

    @Test
    void testDeleteUser2() throws UserNotFoundException {
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser(1));
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testDeleteUser3() throws UserNotFoundException {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsAdmin(true);
        user.setName("Name");
        user.setPassword("password");
        user.setPhoneNumber("4105551212");
        user.setUserId(123);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertTrue(userServiceImpl.deleteUser(1));
        verify(userRepository).findById((Integer) any());
        verify(userRepository).deleteById((Integer) any());
    }

    @Test
    void testDeleteUser4() throws UserNotFoundException {
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser(1));
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testDeleteAll() {
        doNothing().when(userRepository).deleteAll();
        assertTrue(userServiceImpl.deleteAll());
        verify(userRepository).deleteAll();
    }

    @Test
    void testDeleteAll2() {
        doNothing().when(userRepository).deleteAll();
        assertTrue(userServiceImpl.deleteAll());
        verify(userRepository).deleteAll();
    }
}

