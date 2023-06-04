package tests;

import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class SpotifyTrackNegativeTests extends SpotifyBase{

    @Description("Validate top tracks if invalid id is searched.")
    @Test(dataProvider = "idProvider", dataProviderClass = WrongIdDataProvider.class)
    public void getInvalidTrack(String testCaseId, String id){

        searchTopTrack(id).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Description("Validate search results if a random set of strings is searched")
    @Test(dataProvider = "idProvider", dataProviderClass = WrongIdDataProvider.class)
    public void getInvalidArtist(String testCaseId, String id){

        searchArtist(id).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("artists.items",Matchers.hasSize(0));
    }
}