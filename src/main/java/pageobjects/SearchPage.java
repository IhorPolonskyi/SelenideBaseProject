package pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
@NoArgsConstructor
public class SearchPage {

    SelenideElement searchField = $(By.id("SearchInput"));
    SelenideElement emptyProductBlockTitle = $(By.xpath("//*[contains(@class,'ProductsEmpty__title')]"));
    SelenideElement emptyProductBlockDescription = $(By.xpath("//*[contains(@class,'ProductsEmpty__descr')]"));

    ElementsCollection cardProducts = $$(By.xpath("//*[contains(@class,'TMLibraryProductCard__overlay')]"));
    ElementsCollection cardProductsTypes = $$(By.className("TMLibraryProductCard__type"));

    ElementsCollection searchTags = $$(By.xpath("//button[contains(@class,'ProductsSearchForm__tag')]"));
    ElementsCollection searchTagsEmptyBlock = $$(By.xpath("//button[contains(@class,'ProductsEmpty__tag')]"));


    public SearchPage search(String text){
        searchField
                .setValue(text)
                .pressEnter();
        return this;
    }


}
