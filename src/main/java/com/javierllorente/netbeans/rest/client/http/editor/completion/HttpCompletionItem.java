/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.javierllorente.netbeans.rest.client.http.editor.completion;

import com.javierllorente.netbeans.rest.client.http.editor.HttpEditorKit;
import com.javierllorente.netbeans.rest.client.http.editor.models.HttpElement;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JToolTip;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.*;

/**
 *
 * @author Chris
 */
public final class HttpCompletionItem implements CompletionItem {

    private final HttpElement httpElement;
    private static final ImageIcon fieldIcon = new ImageIcon(ImageUtilities.loadImage(HttpEditorKit.HTTP_ICON));

    private static final Color fieldColor = Color.decode("0x0000B2");
    private final int caretOffset;
    private final int dotOffset;

    public HttpCompletionItem(HttpElement httpElement, int dotOffset, int caretOffset) {
        this.httpElement = httpElement;
        this.dotOffset = dotOffset;
        this.caretOffset = caretOffset;
    }

    @Override
    public void defaultAction(JTextComponent jTextComponent) {
        try {
            StyledDocument doc = (StyledDocument) jTextComponent.getDocument();

            doc.remove(dotOffset, caretOffset - dotOffset);
            doc.insertString(dotOffset, httpElement.snippetText, null);

            Completion.get().hideAll();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void processKeyEvent(KeyEvent arg0) {
    }

    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(httpElement.snippetText, null, graphics, font);
//        String text1 = "S" + snippetText.substring(1, snippetText.length() - 1) + "ss";
//        return CompletionUtilities.getPreferredWidth(text1, null, graphics, font);
    }

    @Override
    public void render(Graphics g, Font defaultFont, Color defaultColor, Color backgroundColor, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(fieldIcon, httpElement.snippetText, httpElement.snippetText, g, defaultFont, (selected ? Color.white : fieldColor), width, height, selected);

//            Color backgroundColor, int width, int height, boolean selected) {
//        String text1;
//        if (snippetText.charAt(0) == '<') {
//            text1 = "&lt;" + snippetText.substring(1, snippetText.length() - 1) + "&gt;";
//        } else {
//            text1 = snippetText;
//        }
//        CompletionUtilities.renderHtml(fieldIcon, text1, null, g, defaultFont,
//                (selected ? Color.white : fieldColor), width, height, selected);
    }

    @Override
    public CompletionTask createDocumentationTask() {
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int i) {
            completionResultSet.setDocumentation(new HttpCompletionDocumentation(HttpCompletionItem.this));
                completionResultSet.finish();
            }
        });
    }

    @Override
    public CompletionTask createToolTipTask() {
        return new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int i) {
                JToolTip toolTip = new JToolTip();
                toolTip.setTipText("Press Enter to insert \"" + httpElement.snippetText.substring(1, httpElement.snippetText.length() - 1) + "\"");
                completionResultSet.setToolTip(toolTip);
                completionResultSet.finish();
            }
        });
    }

    @Override
    public boolean instantSubstitution(JTextComponent arg0) {
        return false;
    }

    @Override
    public int getSortPriority() {
        return 0;
    }

    @Override
    public CharSequence getSortText() {
        return httpElement.snippetText;
    }

    public String getText() {
        return httpElement.snippetText;
    }

    @Override
    public CharSequence getInsertPrefix() {
        return httpElement.snippetText;
    }
}
