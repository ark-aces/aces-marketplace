package com.arkaces.aces_marketplace_api.email_verification;

import com.arkaces.aces_marketplace_api.EmailSettings;
import com.arkaces.aces_marketplace_api.common.MustacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailVerificationService {
    
    private final MailSender mailSender;
    private final String verificationEmailTemplate;
    private final String baseUrl;
    private final MustacheService mustacheService;
    private final EmailSettings emailSettings;

    public void sendVerificationEmail(String name, String emailAddress, String verificationCode) {
        String verificationUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + "/email-verification")
            .queryParam("code", verificationCode)
            .build()
            .toString();
        
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("verificationUrl", verificationUrl);

        String from = "\"" + emailSettings.getFromName() + "\" <" + emailSettings.getFromEmailAddress() + ">";
        String subject = "ACES Marketplace Account Created";
        String text = mustacheService.render(verificationEmailTemplate, params);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddress);
        msg.setFrom(from);
        msg.setSubject(subject);
        msg.setText(text);
        
        this.mailSender.send(msg);
    }
}
