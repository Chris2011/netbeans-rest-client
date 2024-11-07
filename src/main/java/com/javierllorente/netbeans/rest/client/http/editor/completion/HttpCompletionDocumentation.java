/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.javierllorente.netbeans.rest.client.http.editor.completion;

import java.net.URL;
import javax.swing.Action;
import org.netbeans.spi.editor.completion.CompletionDocumentation;

/**
 *
 * @author Chris
 */
public class HttpCompletionDocumentation implements CompletionDocumentation {
    public HttpCompletionDocumentation(HttpCompletionItem item) {
        this.item = item;
    }
    
     private HttpCompletionItem item;

    @Override
    public String getText() {
        return "Information about " + item.getText();
    }

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public CompletionDocumentation resolveLink(String string) {
        return null;
    }

    @Override
    public Action getGotoSourceAction() {
        return null;
    }
}
