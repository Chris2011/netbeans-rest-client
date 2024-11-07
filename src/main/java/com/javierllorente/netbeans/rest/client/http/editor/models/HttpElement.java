package com.javierllorente.netbeans.rest.client.http.editor.models;

/**
 *
 * @author Chris
 */
public class HttpElement {
    public String snippetText;
    public String name;

    // name: Basic Base64, type: HttpElementType.AUTHENTICATION, prefix: "Authentication : ", description: "Base64 encoded username and password", snippetText: "Basic \${1:base64-user-password}"
    public HttpElement(String name, HttpElementType type, String prefix, String description, String snippetText) {
        this.snippetText = snippetText;

        if (this.snippetText.isEmpty()) {
            this.snippetText = name;
        }

        if (type.equals(HttpElementType.Header)) {
            this.snippetText += ": ";
        } else if (type.equals(HttpElementType.Method)) {
            this.snippetText += " ";
        }

        if (type.equals(HttpElementType.SystemVariable)) {
            this.name = name.substring(1);
        }
    }

}
