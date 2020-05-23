package com.group1.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final String jarFileLocation;

    public LocationService() {
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        jarFileLocation = applicationHome.getSource().getParent();
        //There are some fucking problems.Shit happens while packaging.So use -Dmaven.test.skip=true to avoid tests.
    }


    public String getStoreHouse() {
        if (!jarFileLocation.equals("/")) {
            return jarFileLocation + "/storehouse";
        } else {
            return "/storehouse";
        }
    }
}