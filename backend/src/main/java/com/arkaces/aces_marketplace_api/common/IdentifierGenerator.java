package com.arkaces.aces_marketplace_api.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IdentifierGenerator {

    private final CodeGenerator codeGenerator;

    public String generate() {
        return codeGenerator.generate(20);
    }

}