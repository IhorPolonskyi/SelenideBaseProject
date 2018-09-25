package storefronttestsuite;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import pageobjects.SearchPage;
import services.BaseTest;
import services.Constants;

import static com.codeborne.selenide.Selenide.open;


public class StorefrontSearchTests extends BaseTest{

    @Test
    public void test_001_successSearch() {

        open(Constants.PRODUCTS_URL);
        new SearchPage()
                .search(Constants.JOOMLA)
                .getCardProductsTypes()
                .shouldHave(CollectionCondition.textsInAnyOrder(Constants.JOOMLA));
    }

    @Test
    public void test_002_unSuccessSearch() {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage()
                .search(Constants.INCORRECT_SEARCH);

        searchPage.getEmptyProductBlockTitle()
                .shouldHave(Condition.text("SORRY"));
        searchPage.getEmptyProductBlockDescription()
                .shouldHave(Condition.text("Try to delete"));
        searchPage.getSearchTagsEmptyBlock().first()
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(Constants.INCORRECT_SEARCH));
    }

    @Test
    public void test_003_deleteTag() {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage();
        searchPage
                .search(Constants.JOOMLA)
                .getSearchTags().first()
                .should(Condition.visible)
                .shouldHave(Condition.text(Constants.JOOMLA))
                .click();

        searchPage.getSearchTags().first()
                .shouldNot(Condition.visible);
        searchPage.getCardProducts()
                .shouldBe(CollectionCondition.sizeGreaterThan(10));
    }

    @Test
    public void test_004_deleteTagAtUnSuccessField() {

        open(Constants.PRODUCTS_URL);

        SearchPage searchPage = new SearchPage();
        searchPage
                .search(Constants.INCORRECT_SEARCH)
                .getSearchTagsEmptyBlock().first()
                .should(Condition.visible)
                .click();

        searchPage.getSearchTagsEmptyBlock().first()
                .shouldNot(Condition.visible);
        searchPage.getCardProducts()
                .shouldBe(CollectionCondition.sizeGreaterThan(10));
    }
}
