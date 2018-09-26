package services;

import org.testng.annotations.DataProvider;
import services.enums.ClickType;

public class DataProviders {

    @DataProvider(name = "clickMethod")
    public static Object[][] clickMethod() {
        return new Object[][] {{ClickType.Enter}, {ClickType.Unfocus}};
    }

    @DataProvider(name = "injections")
    public static Object[][] injections() {
        return new Object[][]
                {{"</title><script>alert('xss')</script>"},
                {"<b onmouseover=\"alert('xss!')\">Hello</b>"},
                {"<script>alert(\"xss!\")</script>"},
                {"<script>document.getElementByID(\"...\").disabled=true</script>"},
                {"<b onmouseover=\"alert('xss!')\">Hello</b>"},
                {" <script>alert(document.cookie);</script>"},
                {"<script type=\"text/javascript\">alert(\"It's a bug! \");</script>"},
        };
    }









}
