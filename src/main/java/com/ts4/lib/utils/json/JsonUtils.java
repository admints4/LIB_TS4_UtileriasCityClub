package com.ts4.lib.utils.json;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;

public class JsonUtils {
    @Bean
    public Gson getGsonInstance() {
        return new Gson();
    }
}
