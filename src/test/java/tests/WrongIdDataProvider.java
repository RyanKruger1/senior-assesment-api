package tests;

import org.testng.annotations.DataProvider;

public class WrongIdDataProvider {

    @DataProvider(name = "idProvider")
    public Object[][] provideData() {

        return new Object[][] {
                {"Case 1", "fadsfhasldfhalsdjflasdfji"},
                {"Case 2", "adsfasdfadsf//asdfasdfadsf"},
                {"Case 3", "asdfasdfasdfasd131231231313"},
                {"Case 4", "4bb518b5ae4b4cc48f5d5bcbf96de80f"},
                {"Case 5", "db83be56-e831-4322-8a18-98c5343e9f46"},
                {"Case 5", "22afe339-0685-4d1b-948b-ac3ba779b543"},
        };
    }
}
