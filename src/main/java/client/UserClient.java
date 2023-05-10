package client;

import burgerapi.BurgerApiPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.User;
import models.UserData;

import static io.restassured.RestAssured.given;

public class UserClient extends StellarBurgersURL {
    @Step("Регистрация пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseRequestSpec())
                .body(user)
                .log().all()
                .post(BurgerApiPoints.USER_PATH + "register")
                .then()
                .log().all();
    }

    @Step("Вход пользователя")
    public ValidatableResponse login(UserData credentials) {
        return given()
                .spec(getBaseRequestSpec())
                .body(credentials)
                .log().all()
                .post(BurgerApiPoints.USER_PATH + "login")
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getBaseRequestSpec())
                .body(accessToken)
                .log().all()
                .delete(BurgerApiPoints.USER_PATH + "user")
                .then()
                .log().all();
    }

    @Step("обновление данных авторизованного пользователя")
    public ValidatableResponse updateUserWithAuth(User user, String accessToken) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", accessToken)
                .body(user)
                .log().all()
                .patch(BurgerApiPoints.USER_PATH + "user")
                .then()
                .log().all();
    }

    @Step("обновление данных неавторизованного пользователя")
    public ValidatableResponse updateUserWithoutAuth(User user) {
        return given()
                .spec(getBaseRequestSpec())
                .body(user)
                .log().all()
                .patch(BurgerApiPoints.USER_PATH + "user")
                .then()
                .log().all();
    }
}