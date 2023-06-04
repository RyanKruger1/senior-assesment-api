import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class BaseAPIClient {

    WebClient webClient = new WebClient();

    static String access_token = "";
    static String refresh_token = "";

    public void startup() {

    }

    public void setup() {

    }

    public void tearDown() {

    }

    @Test
    public void OAuth2Setup() {
        String authorizationUrl = "https://accounts.spotify.com/authorize";
        String clientId = "0c6c763d6a484d77b845bd7670fbb0fc";
        String client_secret = "20154691ba5948ce82b8d99ca8110f0b";
        String redirectUri = "http://localhost:8080";
        // String scope = "<space_separated_scopes>";

        Response response = given()
                .log().all()
                .redirects().follow(false)
                .param("client_id", clientId)
                .param("client_secret", client_secret)
                .param("response_type", "code")
                .param("redirect_uri", redirectUri)
                // .param("scope", scope)
                .get(authorizationUrl).then().log().all().extract().response();


        String authorizationCode = "";

        System.out.println("==================== Redirect to location header ==========================\n\n");
        if (response.getStatusCode() == 303) {
            String redirectUrl = response.getHeader("Location");

            authorizationCode = webClient.navigateToUrl(redirectUrl, "ryankruger45@gmail.com", "Kru@2020");
            System.out.println(authorizationCode);
        }

        String tokenUrl = "https://accounts.spotify.com/api/token";
        String clientSecret = "20154691ba5948ce82b8d99ca8110f0b";

        Response tokenResponse = given()
                .log().all()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "authorization_code")
                .formParam("code", extractCodeFromUrl(authorizationCode))
                .formParam("redirect_uri", "http://localhost:8080")

                .auth().preemptive().basic(clientId, clientSecret).log().all()
                .post(tokenUrl).
                then().log().all().extract().response();

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

        return null; // Code not found in the redirect URL
    }
}
