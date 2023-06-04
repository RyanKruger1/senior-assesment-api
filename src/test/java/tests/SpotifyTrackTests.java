package tests;

import com.google.gson.JsonObject;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class SpotifyTrackTests extends SpotifyBase {

    @Description("Validate that artist has the correct genres associated to it.")
    @Test
    public void search() {
        ValidatableResponse response =
                searchArtist(artist).
                        assertThat().
                        statusCode(HttpStatus.SC_OK).
                        body("artists.items[0].genres", Matchers.hasItem(genre));
    }

    @Description("")
    @Test
    public void validateOneOfTheArtistsTopTracks() {

        String artistId =
                searchArtist(artist).
                        extract().
                        jsonPath().
                        get("artists.items[0].id");

        ValidatableResponse validatableResponse =
                searchTopTrack(artistId).
                        body("tracks.name", Matchers.hasItem(topTrack));
    }

    @Description("")
    @Test
    public void subscribeAndUnsubscribeToArtistTopTrack() {

        String artistId =
                searchArtist(artist).
                        extract().
                        jsonPath().
                        get("artists.items[0].id");

        String id =
                searchTopTrack(artistId).
                        extract().jsonPath().getString("tracks[0].id");

        subscribeToTracks(objectOfIds(id)).
                assertThat().statusCode(HttpStatus.SC_OK);

        checkIfTrackIsSubscribeTo(id).
                assertThat().
                body("[0]", Matchers.is(true));

        unsubscribeToTracks(objectOfIds(id)).
                assertThat().statusCode(HttpStatus.SC_OK);

        checkIfTrackIsSubscribeTo(id).
                assertThat().
                body("[0]", Matchers.is(false));
    }
}