package com.arkaces.aces_marketplace_api.common;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MustacheService {

    private ConcurrentHashMap<String, Mustache> mustacheMap = new ConcurrentHashMap<>();
    
    public String render(String template, Map<String, Object> params) {
        Mustache mustache;
        if (mustacheMap.containsKey(template)) {
            mustache = mustacheMap.get(template);
        } else {
            MustacheFactory mustacheFactory = new DefaultMustacheFactory();
            StringReader reader = new StringReader(template);
            mustache = mustacheFactory.compile(reader, template);
            mustacheMap.put(template, mustache);
        }
        
        StringWriter writer = new StringWriter();
        mustache.execute(writer, params);

        return writer.toString();
    }

}
