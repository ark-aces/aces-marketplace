package com.arkaces.aces_marketplace_api.common;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResourceService {
    
    public String read(String resourcePath) {
        try {
            return Resources.toString(Resources.getResource(resourcePath), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource", e);
        }
    }

}
