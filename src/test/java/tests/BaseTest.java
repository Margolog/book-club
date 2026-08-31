package tests;

import api.ApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static specs.BaseSpec.baseRequestSpec;

public class BaseTest {

    protected static final ApiClient api = new ApiClient();

    @BeforeAll
    protected static void configureApi() {
        RestAssured.requestSpecification = baseRequestSpec;
    }
}

