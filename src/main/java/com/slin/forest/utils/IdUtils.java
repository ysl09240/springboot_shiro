package com.slin.forest.utils;

import java.util.UUID;

public class IdUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
