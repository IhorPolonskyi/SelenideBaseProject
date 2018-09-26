package storefronttestsuite;

import com.codeborne.selenide.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HeaderPagePart;
import services.DataProviders;
import services.enums.ClickType;
import pageobjects.SearchPage;
import services.BaseTest;
import services.Constants;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertEquals;


public class StorefrontSearchTests extends BaseTest{

    @Test
    public void test_001_successSearch() {

        open(Constants.PRODUCTS_URL);
        new SearchPage()
                .search(Constants.JOOMLA, ClickType.Enter)
                .getCardProductsTypes()
                .forEach(type ->
                    //check that all founded products has joomla type
                    type.shouldHave(Condition.text(Constants.JOOMLA)));
    }

    @Test(enabled = false) //bug
    public void test_002_successSearchFromHeader() {

        open("");
        new HeaderPagePart()
                .search(Constants.JOOMLA);

        //check redirect at product page with search
        assertEquals(url(), baseUrl + Constants.PRODUCTS_URL + "?text=" + Constants.JOOMLA);

        new SearchPage()
                .getCardProductsTypes()
                .forEach(type ->
                        //check that all founded products has joomla type
                        type.shouldHave(Condition.text(Constants.JOOMLA)));
    }

    @Test
    public void test_003_unSuccessSearch() {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage()
                .search(Constants.INCORRECT_SEARCH, ClickType.Unfocus);

        //check that empty search block is present
        searchPage.getEmptyProductBlockTitle()
                .shouldHave(Condition.text("SORRY"));
        searchPage.getEmptyProductBlockDescription()
                .shouldHave(Condition.text("Try to delete"));
        searchPage.getSearchTagsEmptyBlock().first()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(Constants.INCORRECT_SEARCH));
    }

    @Test(enabled = false) //bug
    public void test_003_unSuccessSearchFromHeader() {

        open("");

        //search at header
        new HeaderPagePart()
                .search(Constants.INCORRECT_SEARCH);

        //check redirect at product page with search
        assertEquals(url(), baseUrl + Constants.PRODUCTS_URL + "?text=" + Constants.INCORRECT_SEARCH);

        //check that empty search block is present
        SearchPage searchPage = new SearchPage();
        searchPage.getEmptyProductBlockTitle()
                .shouldHave(Condition.text("SORRY"));
        searchPage.getEmptyProductBlockDescription()
                .shouldHave(Condition.text("Try to delete"));
        searchPage.getSearchTagsEmptyBlock().first()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(Constants.INCORRECT_SEARCH));
    }

    @Test
    public void test_004_deleteTag() {

        open(Constants.PRODUCTS_URL);

        //find joomla and than delete tag
        SearchPage searchPage = new SearchPage();
        searchPage
                .search(Constants.JOOMLA, ClickType.Unfocus)
                .getSearchTags().first()
                .should(Condition.visible)
                .shouldHave(Condition.text(Constants.JOOMLA))
                .click();

        //check that tag doesn't present
        searchPage.getSearchTags().first()
                .shouldNot(Condition.visible);
        //check that cards are present
        searchPage.getCardProducts()
                .shouldBe(CollectionCondition.sizeGreaterThan(10));
    }

    @Test
    public void test_005_deleteTagAtUnSuccessField() {

        open(Constants.PRODUCTS_URL);

        //find unsuccess request and than delete tag at unsuccess search block
        SearchPage searchPage = new SearchPage();
        searchPage
                .search(Constants.INCORRECT_SEARCH, ClickType.Unfocus)
                .getSearchTagsEmptyBlock().first()
                .should(Condition.visible)
                .click();

        //check that tag doesn't present
        searchPage.getSearchTagsEmptyBlock().first()
                .shouldNot(Condition.visible);
        //check that cards are present
        searchPage.getCardProducts()
                .shouldBe(CollectionCondition.sizeGreaterThan(10));
    }

    @Test(dataProviderClass = DataProviders.class,dataProvider = "clickMethod", enabled = false)
    public void test_006_successSearchTwoRequests(ClickType type) {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage();
        searchPage
                .search(Constants.JOOMLA, type)
                .search(Constants.CARS, type)
                .getSearchTags().first()
                .should(Condition.visible)
                .click();

        searchPage.getSearchTags().first()
                .shouldHave(Condition.text(Constants.CARS));
        searchPage.getCardProducts()
                .shouldBe(CollectionCondition.sizeGreaterThan(10));
    }


    @Test(enabled = false) //bug\not working
    public void test_007_successSearchByTemplateId() {
        open(Constants.PRODUCTS_URL);
        new HeaderPagePart()
                .search(Constants.MONSTROID_ID);

        //check redirect at monstroid page
        assertEquals(url(), baseUrl + Constants.PRODUCTS_URL + Constants.MONSTROID_ID);
    }

    @Test(enabled = false) //bug\not working
    public void test_008_successSearchAutoCorrection() {

        open(Constants.PRODUCTS_URL);

        //check autocorrection at tag joomla
        SearchPage searchPage = new SearchPage();
        searchPage.search(Constants.JOMLA, ClickType.Enter)
                .getSearchTags()
                .first()
                .shouldHave(Condition.text(Constants.JOMLA));

        //check that search is success
        searchPage.getSearchTags()
                .first().shouldBe(Condition.text(Constants.JOOMLA));

        //check that all founded products has joomla type
        searchPage
                .getCardProducts()
                .shouldHave(CollectionCondition.sizeGreaterThan(10))
                .forEach(type ->
                        type.shouldHave(Condition.text(Constants.JOOMLA)));
    }

    @Test
    public void test_009_searchSuggestions() {

        open("");
        HeaderPagePart headerPagePart = new HeaderPagePart();

        headerPagePart.getSearchField()
                .setValue(Constants.JOOMLA);

        //Check that suggestions contains joomla
        headerPagePart.getSearchSuggestionsProductsNames().forEach(text ->
                Assert.assertTrue(text.contains(Constants.JOOMLA)));

        //Check that suggestions types equals to Joomla template
        headerPagePart.getSearchSuggestionsPlatformTypes().forEach(element ->
                element.shouldHave(Condition.text(Constants.JOOMLA + " template")));
    }

    @Test(dataProviderClass = DataProviders.class,dataProvider = "injections")
    public void test_010_injections(String injection) {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage()
                .search(injection, ClickType.Unfocus);

        //check that tag is visible and something found
        searchPage.getCardProducts()
                .shouldHave(CollectionCondition.sizeGreaterThan(10))
                .first().shouldBe(Condition.visible);
        searchPage.getSearchTags()
                .first()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(injection));
    }
}
