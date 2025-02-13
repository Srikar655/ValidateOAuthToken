package com.iss.Validators;

import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import java.util.List;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final List<String> validAudiences;

    public AudienceValidator(List<String> validAudiences) {
        this.validAudiences = validAudiences;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        List<String> aud = token.getAudience();
        for (String audience : aud) {
            if (validAudiences.contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
        }
        throw new JwtException("Invalid audience: " + aud);
    }
}