package core;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static io.restassured.RestAssured.given;

public class BaseAPIClient {

    WebClient webClient = new WebClient();
    private static String access_token = "";
    public static JsonReader jsonReader;
    public static JsonObject testData;

    @BeforeSuite
    public void startup() {
        jsonReader = new JsonReader();
        testData = jsonReader.readConfigData();
        OAuth2Setup();
    }

    public String getAccess_token() {
        return access_token;
    }

    public void OAuth2Setup() {
        String authorizationUrl = testData.get("accountsUri").getAsString() + "/authorize";
        String tokenUrl = testData.get("accountsUri").getAsString() + "/api/token";

        String clientId = testData.get("clientId").getAsString();
        String clientSecret = testData.get("clientSecret").getAsString();
        String redirectUri = testData.get("redirectUrl").getAsString();
        String scope = "user-library-modify user-library-read playlist-modify-public playlist-modify-private";
        String authorizationCode = "";

        Response response = given()
                .log().all()
                .redirects().follow(false)
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .param("response_type", "code")
                .param("redirect_uri", redirectUri)
                .param("scope", scope)
                .get(authorizationUrl).then().log().all()
                .extract().response();

        if (response.getStatusCode() == 303) {
            String redirectUrl = response.getHeader("Location");

            authorizationCode =
                    webClient.getCallbackWithCode(
                            redirectUrl,
                            testData.get("spotifyUsername").getAsString(),
                            testData.get("spotifyPassword").getAsString());
        }

        Response tokenResponse = given()
                .log().all()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "authorization_code")
                .formParam("code", extractCodeFromUrl(authorizationCode))
                .formParam("redirect_uri", redirectUri)
                .auth().preemptive().basic(clientId, clientSecret).log().all()
                .post(tokenUrl)
                .then().log().all()
                .extract().response();

        access_token = tokenResponse.jsonPath().getString("access_token");
        refresh_token = tokenResponse.jsonPath().getString("refresh_token");
    }

    public static String extractCodeFromUrl(String redirectUrl) {
        String pattern = "code=([^&]+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(redirectUrl);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}