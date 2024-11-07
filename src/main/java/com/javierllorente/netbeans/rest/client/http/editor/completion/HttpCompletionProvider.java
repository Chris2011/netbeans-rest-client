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
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.editor.BaseDocument;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.openide.util.Exceptions;

/**
 *
 * @author Chris
 */
@MimeRegistration(mimeType = HttpEditorKit.HTTP_MIME_TYPE, service = CompletionProvider.class)
public class HttpCompletionProvider implements CompletionProvider {

    @Override
    public CompletionTask createTask(int queryType, JTextComponent component) {
        if (queryType != CompletionProvider.COMPLETION_QUERY_TYPE) {
            return null;
        }

        return new AsyncCompletionTask(new HttpCompletionQuery(), component);
    }

    @Override
    public int getAutoQueryTypes(JTextComponent component, String typedText) {
        return 1; // QueryType.COMPLETION;
    }
    
    private static void checkHideCompletion(final Document doc, final int caretOffset) {
        //test whether we are just in text and eventually close the opened completion
        //this is handy after end tag autocompletion when user doesn't complete the
        //end tag and just types a text
        //test whether the user typed an ending quotation in the attribute value
//        doc.render(() -> {
            TokenHierarchy tokenHierarchy = TokenHierarchy.get(doc);
            TokenSequence tokenSequence = tokenHierarchy.tokenSequence();
            
            tokenSequence.move(caretOffset == 0 ? 0 : caretOffset - 1);
            if (!tokenSequence.moveNext()) {
                return;
            }
            
            Token tokenItem = tokenSequence.token();
//                if (tokenItem.id() == HTMLTokenId.TEXT && !tokenItem.text().toString().startsWith("<") && !tokenItem.text().toString().startsWith("&")) {
                    System.out.println("");
//                }
//        });
    }

    private class HttpCompletionQuery extends AsyncCompletionQuery {

        @Override
        protected void query(CompletionResultSet completionResultSet, Document document, int caretOffset) {
            System.out.println(caretOffset);
            HttpElementItem httpElementItem = new HttpElementItem();
            
            checkHideCompletion(document, caretOffset);

            String filter = null;
            int startOffset = caretOffset - 1;
//
            try {
                final StyledDocument bDoc = (StyledDocument) document;
                final int lineStartOffset = getRowFirstNonWhite(bDoc, caretOffset);
                final char[] line = bDoc.getText(lineStartOffset, caretOffset - lineStartOffset).toCharArray();
                final int whiteOffset = indexOfWhite(line);

                filter = new String(line, whiteOffset + 1, line.length - whiteOffset - 1);

                if (whiteOffset > 0) {
                    startOffset = lineStartOffset + whiteOffset + 1;
                } else {
                    startOffset = lineStartOffset;
                }
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (filter != null) {
                HttpElement[] httpCommands = httpElementItem.HTTP_COMMANDS;

                for (HttpElement httpELement : httpCommands) {
                    final String elementText = httpELement.snippetText;

                    if (!elementText.isEmpty() && elementText.toLowerCase().contains(filter.toLowerCase())) {
                        completionResultSet.addItem(new HttpCompletionItem(httpELement, startOffset, caretOffset));
                    }
                }
            }
            completionResultSet.finish();
        }

    }

    static int getRowFirstNonWhite(StyledDocument doc, int offset) throws BadLocationException {
        Element lineElement = doc.getParagraphElement(offset);
        int start = lineElement.getStartOffset();

        while (start + 1 < lineElement.getEndOffset()) {
            try {
                if (doc.getText(start, 1).charAt(0) != ' ') {
                    break;
                }
            } catch (BadLocationException ex) {
                throw (BadLocationException) new BadLocationException(
                        "calling getText(" + start + ", " + (start + 1)
                        + ") on doc of length: " + doc.getLength(), start).initCause(ex);
            }

            start++;
        }

        return start;
    }

    static int indexOfWhite(char[] line) {
        int i = line.length;
        while (--i > -1) {
            final char c = line[i];
            if (Character.isWhitespace(c)) {
                return i;
            }
        }
        return -1;
    }
}
