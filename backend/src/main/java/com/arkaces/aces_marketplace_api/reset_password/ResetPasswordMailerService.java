package com.arkaces.aces_marketplace_api.reset_password;

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
public class ResetPasswordMailerService {
    
    private final MailSender mailSender;
    private final String resetPasswordEmailTemplate;
    private final String baseUrl;
    private final MustacheService mustacheService;
    private final EmailSettings emailSettings;

    public void sendResetPasswordLink(String name, String emailAddress, String code) {
        String resetPasswordUrl = UriComponentsBuilder.fromHttpUrl(baseUrl + "/reset-password")
            .queryParam("code", code)
            .build()
            .toString();
        
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("resetPasswordUrl", resetPasswordUrl);

        String from = "\"" + emailSettings.getFromName() + "\" <" + emailSettings.getFromEmailAddress() + ">";
        String subject = "ACES Marketplace Reset Password Request";
        String text = mustacheService.render(resetPasswordEmailTemplate, params);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddress);
        msg.setFrom(from);
        msg.setSubject(subject);
        msg.setText(text);
        
        this.mailSender.send(msg);
    }
}
