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

public class ContentBottomToolBarPresenter implements ContentBottomToolBarComponent {

    private ContentBottomToolBarView view;

    public void init(final ContentBottomToolBarView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setRate(final boolean isRateable, boolean isLogged, final Double value, final Integer rateByUsers,
            final Double currentUserRate) {
        if (isRateable) {
            if (!isLogged) {
                setRate(value, rateByUsers);
            } else {
                setRate(value, rateByUsers, currentUserRate);
            }
        } else {
            setRateVisible(false);
            setRateItVisible(false);
        }
    }

    private void setRate(final Double value, final Integer rateByUsers, final Double currentUserRate) {
        view.setRate(value, rateByUsers);
        view.setRateIt(currentUserRate);
        setRateVisible(true);
        setRateItVisible(true);
    }

    private void setRate(final Double value, final Integer rateByUsers) {
        view.setRate(value, rateByUsers);
        setRateVisible(true);
        setRateItVisible(false);
    }

    private void setRateVisible(final boolean visible) {
        view.setRateVisible(visible);
    }

    private void setRateItVisible(final boolean visible) {
        view.setRateItVisible(visible);
    }

    public void setEnabledRateIt(final boolean enabled) {
        view.setRateItVisible(enabled);
    }

}
