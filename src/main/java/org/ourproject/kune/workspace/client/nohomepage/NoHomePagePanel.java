/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.nohomepage;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.EntityWorkspace;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Label;

public class NoHomePagePanel implements NoHomePageView {

    private final Label noHomepageCtnLabel;
    private final Label noHomepageCtxLabel;
    private final WorkspaceSkeleton ws;

    public NoHomePagePanel(final NoHomePagePresenter presenter, final WorkspaceSkeleton ws, I18nTranslationService i18n) {
        this.ws = ws;
        noHomepageCtnLabel = new Label(i18n.t("This user don't have a homepage"));
        noHomepageCtnLabel.setStyleName("kune-Content-Main");
        noHomepageCtnLabel.addStyleName("kune-Margin-7-trbl");
        noHomepageCtxLabel = new Label("");
    }

    public void clearWs() {
        EntityWorkspace ew = ws.getEntityWorkspace();
        ew.setContent(noHomepageCtnLabel);
        ew.setContext(noHomepageCtxLabel);
        ew.getContentTopBar().removeAll();
        ew.getContextTopBar().removeAll();
        ew.getContentBottomBar().removeAll();
        ew.getContextBottomBar().removeAll();
    }
}