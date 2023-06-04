package tests;

import core.BaseAPIClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class SpotifyBase extends BaseAPIClient {

    String artist;
    String genre;
    String topTrack;

    @BeforeMethod
    public void setupSpotifyBase() {
        RestAssured.baseURI = "https://api.spotify.com/v1";

        artist = testData.get("artistUnderTest").getAsString();
        genre = testData.get("artistGenre").getAsString();
        topTrack = testData.get("artistTopTrack").getAsString();
    }

    public ValidatableResponse searchArtist(String artist) {

        return given().
                param("access_token", getAccess_token()).
                param("q", artist).
                param("type", "artist").
                param("limit", "1").
                contentType(ContentType.JSON).
                log().all().
                when().
                get("/search").
                then().
                log().all();
    }

    public ValidatableResponse searchTopTrack(String artistId) {

        return given().
                param("access_token", getAccess_token()).
                param("market", "za").
                param("limit", "1").
                contentType(ContentType.JSON).
                log().all().
                when().
                get("/artists/" + artistId + "/top-tracks").
                then().
                log().all();
    }

    public ValidatableResponse checkIfTrackIsSubscribeTo(String id) {

        return given().
                param("access_token", getAccess_token()).
                param("ids", id).
                contentType(ContentType.JSON).
                log().all().
                when().
                get("/me/tracks/contains").
                then().
                log().all();
    }

    JSONObject objectOfIds(String id) {
        JSONArray array = new JSONArray();
        array.add(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ids", array);

        return jsonObject;
    }

    public ValidatableResponse subscribeToTracks(JSONObject objectOfIds) {
        return given().
                param("access_token", getAccess_token()).
                contentType(ContentType.JSON).
                log().all().
                body(objectOfIds).
                when().
                put("/me/tracks").
                then().
                log().all();
    }

    public ValidatableResponse unsubscribeToTracks(JSONObject objectOfIds) {
        return given().
                param("access_token", getAccess_token()).
                contentType(ContentType.JSON).
                log().all().
                body(objectOfIds).
                when().
                delete("/me/tracks").
                then().
                log().all();
    }
}