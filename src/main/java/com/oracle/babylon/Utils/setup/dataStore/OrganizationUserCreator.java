package com.oracle.babylon.Utils.setup.dataStore;

import com.github.javafaker.Address;
import com.github.javafaker.Company;
import com.github.javafaker.Faker;
import com.oracle.babylon.Utils.setup.dataStore.pojo.Organization;
import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;

import java.util.Locale;

/**
 * Class to convert the data tables from the test files to data store tables for a organization
 * Author : susgopal
 */
public class OrganizationUserCreator {

    private Organization organization = new Organization();
    private Faker faker = new Faker(new Locale("en-US"));
    private User user = new User();

    private String name = null;

    /**
     * Function to set the values of the organization with data from Faker class
     */
    public void generateOrgData(){

        ConfigFileReader configFileReader = new ConfigFileReader();
        Company company = faker.company();
        String companyName = company.name();
        organization.setOrganizationName(companyName);
        Address address = faker.address();
        organization.setAddress(address.streetAddress());
        organization.setCity(address.city());
        organization.setCounty(address.state());
        organization.setPostcode(address.zipCode());
        organization.setCountry(configFileReader.getCountryName());
        organization.setTradingName(companyName.substring(0,3).toUpperCase());
        organization.setOrgAbbreviation(companyName.substring(0,6).toLowerCase());
        organization.setContactEmailAddress(configFileReader.getEmailId());
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        name = firstName + " " + lastName;
        organization.setContactFirstName(firstName);
        organization.setContactLastName(lastName);
        organization.setContactPhone(faker.phoneNumber().phoneNumber());
        //Store the data in the data store
        new DataStore().storeOrganizationInfo("organization", organization);

    }

    /**
     * Function to create data and set in the User pojo
     */
    public void addUser(){
        user.setUsername(faker.name().username());
        if(name!=null){
            user.setFullName(name);
        } else{
            user.setFullName(faker.name().firstName() + " " + faker.name().lastName());
        }

        String password = faker.internet().password() + "8";
        user.setPassword(password);
        //Store the values in a data store with the name user
        new DataStore().addUser("user", user);
    }


}
