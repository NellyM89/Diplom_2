package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class StellarBurgersURL {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    protected RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}