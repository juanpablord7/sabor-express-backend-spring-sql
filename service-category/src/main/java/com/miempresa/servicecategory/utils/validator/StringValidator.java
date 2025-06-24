package com.miempresa.servicecategory.utils.validator;

import java.util.regex.Pattern;

public class StringValidator {

    // Patron de caracteres para detectar XSS
    private static final Pattern XSS_PATTERN = Pattern.compile(
            "(?ix)" +           //i = ignore case, x = verbose (allow whitespace and comments)
            "<[^>]+>" +         //cualquier etiqueta HTML
            "|javascript:" +    //protocolo javascript
            "|onerror\\s*=" +   //evento onerror=
            "|onload\\s*=" +    //evento onload=
            "|script"           //palabra 'script'
            , Pattern.CASE_INSENSITIVE);

    public static Boolean validateXSS(String input) {
        if (input == null) return false;

        if (XSS_PATTERN.matcher(input).find()) {
            return true;
        }

        return false;
    }

}
