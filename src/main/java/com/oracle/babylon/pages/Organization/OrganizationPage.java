package com.oracle.babylon.pages.Organization;

import com.codeborne.selenide.WebDriverRunner;
import com.oracle.babylon.Utils.helper.Navigator;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Organization;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;

public class OrganizationPage extends Navigator {

    //Initialization of the Web Elements
    private By orgNameTxtBox = By.name("ORG_NAME");
    private By addressTxtBox = By.name("POSTAL_ADDRESS1");
    private By cityTxtBox = By.name("POSTAL_SUBURB");
    private By stateTxtBox = By.name("POSTAL_STATE");
    private By countrySelectBox = By.name("POSTAL_COUNTRY");
    private By postCodeTxtBox = By.name("POSTAL_POSTCODE");
    private By tradingNameTxtBox = By.name("TRADING_NAME");
    private By orgAbbreviationTxtBox = By.name("ORG_CODE");
    private By firstNameTxtBox = By.name("USER_FIRST_NAME");
    private By lastNameTxtBox = By.name("USER_LAST_NAME");

    private By emailIdTxtBox = By.name("EMAIL");
    private By phoneTxtBox = By.name("USER_PHONE");
    private By loginNameTxtBox = By.name("USER_NAME");
    private By passwordTxtBox = By.name("PASSWORD");
    private By confirmPasswordTxtBox = By.name("passwordConfirm");
    private By acceptTermsChkBox = By.name("acceptTermsOfService");
    private By registerBtn = By.xpath("//button//span[text()='Register']");
    private By thankYouMessage = By.xpath("//p[text()='Thank you, your registration is now being processed.']");


    /**
     * Fill up all the fields of the Create Organization page using Selenium libraries
     */
    public void fillOrganizationDetails() throws InterruptedException {
        //Initialization of references
        this.driver = WebDriverRunner.getWebDriver();

        //Get Organization and User pojos
        Organization organization = dataStore.getOrganizationInfo("organization");
        User userInfo = dataStore.getUser("user");

        commonMethods.waitForElement(this.driver, orgNameTxtBox, 3);
        //Filling up the organization fields in UI by using Selenide
        $(orgNameTxtBox).sendKeys(organization.getOrganizationName());
        $(addressTxtBox).sendKeys(organization.getAddress());
        $(cityTxtBox).sendKeys(organization.getCity());
        $(stateTxtBox).sendKeys(organization.getCounty());
        Select select = new Select($(countrySelectBox));
        select.selectByValue(organization.getCountry());
        $(postCodeTxtBox).sendKeys(organization.getPostcode());
        //$(tradingNameTxtBox).sendKeys(organization.getTradingName());
        //$(orgAbbreviationTxtBox).sendKeys(organization.getOrgAbbreviation());
        $(firstNameTxtBox).sendKeys(organization.getContactFirstName());
        $(lastNameTxtBox).sendKeys(organization.getContactLastName());
        $(emailIdTxtBox).sendKeys(organization.getContactEmailAddress());
        $(phoneTxtBox).clear();
        $(phoneTxtBox).sendKeys(organization.getContactPhone());
        $(loginNameTxtBox).sendKeys(userInfo.getUserName());
        $(passwordTxtBox).sendKeys(userInfo.getPassword().toString());
        $(confirmPasswordTxtBox).sendKeys(userInfo.getPassword().toString());
        $(acceptTermsChkBox).click();
        Thread.sleep(3000);
        $(registerBtn).click();
        commonMethods.waitForElement(this.driver, thankYouMessage);
        $(thankYouMessage).isDisplayed();
    }

    /**
     * Function to fill the details the organization to a file
     * @return a map of user
     * @throws IOException
     * @throws ParseException
     */
    public User enterOrgUserDetailsToFile() throws IOException, ParseException {
        Organization organization = dataStore.getOrganizationInfo("organization");
        User userInfo = dataStore.getUser("user");
        String[] userKeyList = {"user", "username"};
        dataSetup.writeIntoJson(userKeyList, userInfo.getUserName(), configFileReader.returnUserDataJsonFilePath());

        userKeyList = new String[]{"user", "password"};
        dataSetup.writeIntoJson(userKeyList, userInfo.getPassword(), configFileReader.returnUserDataJsonFilePath());

        userKeyList = new String[]{"user", "firstname"};
        dataSetup.writeIntoJson(userKeyList, userInfo.getFullName(), configFileReader.returnUserDataJsonFilePath());

        String[] orgList = {"organization", "orgname"};
        dataSetup.writeIntoJson(orgList, organization.getOrganizationName(), configFileReader.returnUserDataJsonFilePath());
        return userInfo;

    }


}