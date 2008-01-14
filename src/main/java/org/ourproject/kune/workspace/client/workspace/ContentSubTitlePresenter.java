/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class ContentSubTitlePresenter implements ContentSubTitleComponent {

    private ContentSubTitleView view;

    public void init(final ContentSubTitleView view) {
        this.view = view;
    }

    public void setState(final StateDTO state) {
        if (state.hasDocument()) {
            view.setContentSubTitleLeft(Kune.I18N.tWithNT("by: [%s]", "used in a list of authors",
                    ((UserSimpleDTO) state.getAuthors().get(0)).getName()));
            view.setContentSubTitleLeftVisible(true);
        } else {
            view.setContentSubTitleLeftVisible(false);
        }
        if (state.getLanguage() != null) {
            String langName = state.getLanguage().getEnglishName();
            setContentLanguage(langName);
            view.setContentSubTitleRightVisible(true);
        } else {
            view.setContentSubTitleRightVisible(false);
        }
    }

    public void setContentLanguage(final String langName) {
        view.setContentSubTitleRight(Kune.I18N.t("Language: [%s]", langName));
    }

    public View getView() {
        return view;
    }

}
