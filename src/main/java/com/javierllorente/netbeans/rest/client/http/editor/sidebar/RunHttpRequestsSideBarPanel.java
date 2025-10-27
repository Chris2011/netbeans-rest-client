/*
 * Copyright 2025 Christian Lenz <christian.lenz@gmx.net>.
 *
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
package com.javierllorente.netbeans.rest.client.http.editor.sidebar;

import com.javierllorente.netbeans.rest.client.http.editor.sidebar.request.IRequestProcessor;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**
 * Sidebar Panel to display task-related UI elements.
 */
public class RunHttpRequestsSideBarPanel extends JPanel {

    public RunHttpRequestsSideBarPanel(JTextComponent target, IRequestProcessor taskProcessor) {
        super(new BorderLayout());
        add(new DrawingPanel(target, taskProcessor));
    }
}
