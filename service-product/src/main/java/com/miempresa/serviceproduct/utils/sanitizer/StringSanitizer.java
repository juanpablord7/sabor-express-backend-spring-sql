package com.miempresa.serviceproduct.utils.sanitizer;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Component;

@Component
public class StringSanitizer {
    PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public String sanitizeXSS(String input){
        return policy.sanitize(input);
    }

}
