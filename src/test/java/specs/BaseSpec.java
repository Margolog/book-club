package specs;

import config.ApiConfig;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import static allure.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class BaseSpec {

    private static final ApiConfig config = ConfigFactory.create(ApiConfig.class, System.getProperties());

    public static RequestSpecification baseRequestSpec = with()
            .filter(withCustomTemplate())
            .log().all()
            .contentType(JSON)
            .baseUri(config.baseUri())
            .basePath(config.basePath());
}
