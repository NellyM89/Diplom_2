package UserTests;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.User;
import models.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import datautils.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    private String accessToken;
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        try {
            userClient.delete(accessToken);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginUserTest() {
        UserData userCredentials = new UserData(user.getEmail(), user.getPassword());
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        int statusCode = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(SC_OK));
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Логин с неверным email")
    public void loginUserTestWithNonexistentEmailTest() {
        UserData userCredentials = new UserData("nonexistentEmail", user.getPassword());
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        int statusCode = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(SC_UNAUTHORIZED));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginUserTestWithNonexistentPasswordTest() {
        UserData userCredentials = new UserData(user.getEmail(), "nonexistentPassword");
        ValidatableResponse loginResponse = userClient.login(userCredentials);
        int statusCode = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(SC_UNAUTHORIZED));
    }
}
