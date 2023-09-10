//package com.javafx_project.tests;
//
//import com.javafx_project.controllers.LoginController;
//import com.javafx_project.dao.UserDAO;
//import com.javafx_project.models.User;
//import javafx.beans.value.ObservableBooleanValue;
//import javafx.event.ActionEvent;
//import org.mockito.Mockito;
//import org.testng.annotations.Test;
//import org.junit.Before;
//
//import java.sql.SQLException;
//
//import static javafx.beans.binding.Bindings.when;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class LoginControllerTest {
////TODO create tests for crud operations and fix login test
//    private LoginController controller;
//    private UserDAO mockUserDAO;
//
//    @Before
//    public void setup() {
//        controller = new LoginController();
//        mockUserDAO = Mockito.mock(UserDAO.class);
//        controller.userDAO = mockUserDAO;
//    }
//
//    @Test
//    public void testSuccessfulLogin() throws SQLException {
//        User mockUser = new User(1, "test", "password");
//
//        when((ObservableBooleanValue) mockUserDAO.getUserByUsername("test")).then(mockUser);
//
//        controller.usernameField.setText("test");
//        controller.passwordField.setText("password");
//
//        controller.login(new ActionEvent());
//
//        assertNotNull(controller.loggedInUser);
//        assertEquals(mockUser, controller.loggedInUser);
//    }
//
//    @Test
//    public void testFailedLogin() throws SQLException {
//        User mockUser = new User(1, "test", "wrongpassword");
//
//        when((ObservableBooleanValue) mockUserDAO.getUserByUsername("test")).then(mockUser);
//
//        controller.usernameField.setText("test");
//        controller.passwordField.setText("password");
//
//        controller.login(new ActionEvent());
//
//        assertNull(controller.loggedInUser);
//    }
//
//}
