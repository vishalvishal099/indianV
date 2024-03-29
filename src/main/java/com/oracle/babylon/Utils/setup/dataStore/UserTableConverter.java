package com.oracle.babylon.Utils.setup.dataStore;


import com.oracle.babylon.Utils.setup.dataStore.pojo.User;
import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import io.cucumber.datatable.DataTable;

import java.util.Map;

/**
 * Class that converts the user data table and set the values
 * Author : vsingsi
 */
public class UserTableConverter {
    private User user = new User();
    /**
     * Function to convert the user data and stores it in a data store
     *
     * @param name name of the data table
     * @param dataTable contents of the data table
     */
    public void addUser(String name, DataTable dataTable) {
        Map<String, String> hashUser = dataTable.transpose().asMap(String.class, String.class);
        user.setFullName(hashUser.get("Fullname"));
        user.setUsername(hashUser.get("Username"));
        user.setProjectName(hashUser.get("Projects"));
        String password = hashUser.containsKey("password") ? hashUser.get("password") : new ConfigFileReader().getPassword();
        user.setPassword(password);
        new DataStore().addUser(name, user);


    }
}
