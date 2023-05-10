package client;

import burgerapi.BurgerApiPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends StellarBurgersURL {

    @Step("Создание заказа авторизованного пользователя")
    public ValidatableResponse createOrderWithAuth(Order order, String accessToken) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", accessToken)
                .body(order)
                .log().all()
                .post(BurgerApiPoints.ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Создание заказа неавторизованного пользователя")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(getBaseRequestSpec())
                .body(order)
                .log().all()
                .post(BurgerApiPoints.ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Получение заказа авторизованного пользователя")
    public ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .spec(getBaseRequestSpec())
                .header("Authorization", accessToken)
                .log().all()
                .get(BurgerApiPoints.ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Получение заказа неавторизованного пользователя")
    public ValidatableResponse getOrderWithoutAuth() {
        return given()
                .spec(getBaseRequestSpec())
                .log().all()
                .get(BurgerApiPoints.ORDER_PATH)
                .then()
                .log().all();
    }
}
