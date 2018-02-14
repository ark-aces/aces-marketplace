package com.arkaces.aces_marketplace_api.server_info;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerInfoController {
    
    @GetMapping("/")
    public ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName("ACES Marketplace");
        serverInfo.setVersion("1.0.0-alpha");
        serverInfo.setConsoleUrl("https://marketplace.arkaces.com");
        return serverInfo;
    }
}
