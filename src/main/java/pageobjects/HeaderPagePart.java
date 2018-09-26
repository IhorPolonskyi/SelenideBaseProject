package pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;


import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
@NoArgsConstructor
public class HeaderPagePart {

    private SelenideElement searchField = $(By.xpath("//input[contains(@class,'AppSearchForm')]"));
    private ElementsCollection searchSuggestions = $$(By.xpath("//*[contains(@class,'SearchSuggestion__link')]"));
    private ElementsCollection searchSuggestionsPlatformTypes =
            $$(By.xpath("//*[contains(@class,'SearchSuggestion__platform')]"));


    public HeaderPagePart search(String text){
        searchField
                .setValue(text)
                .pressEnter();
        return this;
    }

    public List<String> getSearchSuggestionsProductsNames(){
        return searchSuggestions.stream()
                .map(suggestion ->
                    suggestion.getAttribute("aria-label"))
                .collect(Collectors.toList());
    }

}
