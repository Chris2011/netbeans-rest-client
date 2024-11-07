package com.javierllorente.netbeans.rest.client.http.editor.completion;

import com.javierllorente.netbeans.rest.client.http.editor.models.HttpElement;
import com.javierllorente.netbeans.rest.client.http.editor.models.HttpElementType;

/**
 *
 * @author Chris
 */
// GET <request target> expected
public class HttpElementItem {
    public final HttpElement[] HTTP_COMMANDS = {
        new HttpElement("GET", HttpElementType.Method, null, null, ""),
        new HttpElement("POST", HttpElementType.Method, null, null, ""),
        new HttpElement("PUT", HttpElementType.Method, null, null, ""),
        new HttpElement("DELETE", HttpElementType.Method, null, null, ""),
        new HttpElement("PATCH", HttpElementType.Method, null, null, ""),
        new HttpElement("HEAD", HttpElementType.Method, null, null, ""),
        new HttpElement("OPTIONS", HttpElementType.Method, null, null, ""),
        new HttpElement("TRACE", HttpElementType.Method, null, null, ""),
        new HttpElement("CONNECT", HttpElementType.Method, null, null, ""),
            
        new HttpElement("localhost:8080", HttpElementType.URL, null, null, ""),
        new HttpElement("localhost", HttpElementType.URL, null, null, ""),
        new HttpElement("http://", HttpElementType.URL, null, null, ""),
        new HttpElement("https://", HttpElementType.URL, null, null, ""),
        new HttpElement("http://localhost", HttpElementType.URL, null, null, ""),
        new HttpElement("http://localhost:8080", HttpElementType.URL, null, null, ""),
        new HttpElement("https://localhost", HttpElementType.URL, null, null, ""),
        new HttpElement("https://localhost:8080", HttpElementType.URL, null, null, ""),
    };
}
