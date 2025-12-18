package com.renanresende.bridgetotalk.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

public class YamlPropertySourceFactory implements PropertySourceFactory {


    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        return new YamlPropertySourceLoader()
                .load(resource.getResource().getFilename(), resource.getResource())
                .get(0);
    }

}
