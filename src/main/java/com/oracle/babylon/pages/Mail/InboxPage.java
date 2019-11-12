package com.oracle.babylon.pages.Mail;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.CommonMethods;
import com.oracle.babylon.Utils.helper.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

/**
 * Class to hold the functions related to the the mail inbox page
 * Author : visinghsi
 */
public class InboxPage {
    //Initialization of objects and assigning them references
    WebDriver driver = WebDriverRunner.getWebDriver();
    Navigator navigator = new Navigator();

    //Initializing the web elements
    private By mailNumberTextBox = By.id("rawQueryText");
    private By searchBtn = By.xpath("//button[@title='Search']");
    private By loadingIcon = By.cssSelector(".loading_progress");
    private By mailNoFromTable = By.xpath("//td[@class='column_documentNo']");

    /**
     * Function to navigate to a sub menu from the Aconex home page
     */
    public void selectMenuSubMenu() {
        navigator.getMenuSubmenu("Mail", "Inbox");
        CommonMethods commonMethods = new CommonMethods();
        driver = commonMethods.switchToFrame(driver, "frameMain");
    }

    /**
     * Search the mail using the mail number key
     *
     * @param mail_number
     */
    public void searchMailNumber(String mail_number) {
        CommonMethods commonMethods = new CommonMethods();
        $(mailNumberTextBox).sendKeys(mail_number);
        commonMethods.waitForElementExplicitly(2000);
        $(searchBtn).click();
        //Wait for the results to be retrieved
        By by = By.xpath("//span[text()='" + mail_number + "']");
        commonMethods.waitForElement(driver, by, 5000);
        $(loadingIcon).should(disappear);

    }


    public int searchResultCount() {
        return driver.findElements(By.cssSelector(".dataRow")).stream().filter(e -> e.isDisplayed()).collect(Collectors.toList()).size();
    }

    public String getMailNumber() {
        CommonMethods commonMethods = new CommonMethods();
        return driver.findElement(mailNoFromTable).getText();
    }
}
