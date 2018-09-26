package pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import services.enums.ClickType;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
@NoArgsConstructor
public class SearchPage {

    private SelenideElement searchField = $(By.id("SearchInput"));
    private SelenideElement searchPageTitle = $(By.xpath("//h1"));
    private SelenideElement emptyProductBlockTitle = $(By.xpath("//*[contains(@class,'ProductsEmpty__title')]"));
    private SelenideElement emptyProductBlockDescription = $(By.xpath("//*[contains(@class,'ProductsEmpty__descr')]"));

    private ElementsCollection cardProducts = $$(By.xpath("//*[contains(@class,'TMLibraryProductCard__overlay')]"));
    private ElementsCollection cardProductsTypes = $$(By.className("TMLibraryProductCard__type"));

    private ElementsCollection searchTags = $$(By.xpath("//button[contains(@class,'ProductsSearchForm__tag')]"));
    private ElementsCollection searchTagsEmptyBlock = $$(By.xpath("//button[contains(@class,'ProductsEmpty__tag')]"));
    private ElementsCollection detailButtons =
            $$(By.xpath("//article//a[not(contains(@href,'demo')) and contains(@class,'TMLibraryProductCard')]"));

    public SearchPage search(String text, ClickType type){
        searchField.setValue(text);
        switch (type){
            case Enter:
                searchField.pressEnter();
            case Unfocus:
                searchPageTitle.click();
        }
        return this;
    }

    public List<String> getAllTemplateId(){
        List<String> templatesIds = new ArrayList<>();
        detailButtons.forEach(element ->{
            String href = element.getAttribute("href");
            templatesIds.add(href.substring(href.length()-6, href.length()-1));
        });
        return templatesIds;
    }
}


