package pageobjects;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import services.enums.SortingTypes;

import static com.codeborne.selenide.Selenide.$;

@Data
@NoArgsConstructor
public class FiltersPagePart {

    public FiltersPagePart openSortingDropDown(SortingTypes types){
        $(By.xpath("//*[contains(@class,'ProductsPropertiesFilter')]//button[@aria-label='"
                + types.getName() + "']//span"))
        .click();
        return this;
    }

    public FiltersPagePart selectCheckBoxByName(String selectName){
        $(By.xpath("//*[contains(@class,'FilterOptionCheckbox')]//*[@alt='" + selectName + "']"))
                .click();
        return this;
    }

    public FiltersPagePart selectFeatureByName(String name){
        $(By.xpath("//*[@aria-label='" + name + "']")).click();
        return this;
    }

}
