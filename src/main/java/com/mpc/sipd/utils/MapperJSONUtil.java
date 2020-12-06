package com.mpc.sipd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

@Slf4j
public class MapperJSONUtil {
    public static String prettyLog (Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (IOException e){

            log.info("logging error");
            return null;
        }
    }
}
