package storefronttestsuite;

import com.codeborne.selenide.Condition;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.testng.annotations.Test;
import pageobjects.FiltersPagePart;
import pageobjects.SearchPage;
import services.BaseTest;
import services.Constants;
import services.enums.SortingTypes;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static io.restassured.RestAssured.given;

@Log
public class SortingTests extends BaseTest {

    @Test
    public void test_001_platformTypesSorting() {

        open(Constants.PRODUCTS_URL);
        new FiltersPagePart()
               .openSortingDropDown(SortingTypes.PLATFORM_TYPES)
               .selectCheckBoxByName(Constants.WORDPRESS_THEMES);

        SearchPage searchPage = new SearchPage();

        searchPage.getSearchTags().first()
                .shouldHave(Condition.text(Constants.WORDPRESS_THEMES.toLowerCase()));
        searchPage.getCardProductsTypes()
                .forEach(type ->
                        //check that all founded products has wordpress type
                        type.shouldHave(Condition.text(Constants.WORDPRESS_THEMES)));

        assertTrue(url().contains("bestsellers/types/wordpress_themes/"));

        //check template types at api
        searchPage.getAllTemplateId().forEach(id ->
                assertEquals(
                given().contentType(ContentType.JSON)
                        .log().method().log().uri()
                        .when().get(Constants.PRODUCTS_V2_URL + id)
                        .then().statusCode(200)
                        .extract().body().jsonPath().getString("typeName"),
                Constants.WORDPRESS_THEME));
    }

    @Test
    public void test_002_platformTypesTwoTypesSorting() {

        open(Constants.PRODUCTS_URL);
        new FiltersPagePart()
                .openSortingDropDown(SortingTypes.PLATFORM_TYPES)
                .selectCheckBoxByName(Constants.WORDPRESS_THEMES)
                .openSortingDropDown(SortingTypes.PLATFORM_TYPES)
                .selectCheckBoxByName(Constants.WEBSITE_TEMPLATES);

        SearchPage searchPage = new SearchPage();
        searchPage.getSearchTags().first()
                .shouldHave(Condition.text(Constants.WORDPRESS_THEMES.toLowerCase()));
        searchPage.getSearchTags().get(1)
                .shouldHave(Condition.text(Constants.WEBSITE_TEMPLATES.toLowerCase()));

        searchPage.getCardProductsTypes()
                .forEach(type ->
                        //check that all founded products has wordpress type
                                assertTrue(
                                        type.getText().contains(Constants.WEBSITE_TEMPLATES)
                                                |type.getText().contains(Constants.WORDPRESS_THEMES)));

        assertTrue(url().contains("bestsellers/types/wordpress_themes/website_templates/"));

        //check template types at api
        searchPage.getAllTemplateId().forEach(id ->{
            String templateTypeApi = given()
                    .contentType(ContentType.JSON)
                    .log().method().log().uri()
                    .when().get(Constants.PRODUCTS_V2_URL + id)
                    .then().statusCode(200)
                    .extract().body().jsonPath().getString("typeName");

            assertTrue(
                    templateTypeApi.contains(Constants.WEBSITE_TEMPLATE)
                            |templateTypeApi.contains(Constants.WORDPRESS_THEME));
        });
    }

    @Test
    public void test_003_topicsSorting() {

        open(Constants.PRODUCTS_URL);
        new FiltersPagePart()
                .openSortingDropDown(SortingTypes.TOPICS)
                .selectCheckBoxByName(Constants.MEDICAL_TEMPLATES);

        SearchPage searchPage = new SearchPage();

        searchPage.getSearchTags().first()
                .shouldHave(Condition.text(Constants.MEDICAL_TEMPLATES.toLowerCase()));

        assertTrue(url().contains("bestsellers/topics/medical_templates/"));

        //check template types at api
        searchPage.getAllTemplateId().forEach(id ->
                assertEquals(
                        given().contentType(ContentType.JSON)
                                .log().method().log().uri()
                                .when().get(Constants.PRODUCTS_V2_URL + id)
                                .then().statusCode(200)
                                .extract().body().jsonPath().getString("categoryName"),
                        Constants.MEDICAL_TEMPLATES));
    }

    @Test
    public void test_004_featuresSorting() {

        open(Constants.PRODUCTS_URL);
        new FiltersPagePart()
                .openSortingDropDown(SortingTypes.FEATURES)
                .selectFeatureByName(Constants.RESPONSIVE);

        SearchPage searchPage = new SearchPage();
        searchPage.getSearchTags().first()
                .shouldHave(Condition.text(Constants.RESPONSIVE.toLowerCase()));

        assertTrue(url().contains("bestsellers/features/responsive/"));

        //check template types at api
        searchPage.getAllTemplateId().forEach(id ->
                assertEquals(
                        given().contentType(ContentType.JSON)
                                .log().method().log().uri()
                                .when().get(Constants.PRODUCTS_V2_URL + id)
                                .then().statusCode(200)
                                .extract().body().jsonPath().getList("features")
                                .get(0)
                                .toString(),
                        Constants.RESPONSIVE));
    }

}


