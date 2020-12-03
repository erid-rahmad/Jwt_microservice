package com.mpc.sipd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;

@Log4j2
public class MapperJSONUtil {
    public static String prettyLog (Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (IOException e){
            log.info("Logging error");
            return null;
        }
    }
}
