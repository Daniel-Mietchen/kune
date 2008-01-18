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

package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class KuneUiUtils {

    public static void setQuickTip(final Widget widget, final String tip) {
        setQuickTip(widget.getElement(), tip);
    }

    public static void setQuickTip(final Widget widget, final String tip, final String tipTitle) {
        setQuickTip(widget.getElement(), tip, tipTitle);
    }

    /**
     * Use widget.setTitle if you don't want to use extjs (but remember that
     * title is limited to 64chars).
     * 
     * Bug: extjs quicktips don't work with images or span inside divs (like
     * pushbutton). See: http://extjs.com/forum/showthread.php?t=22293
     * http://extjs.com/forum/showthread.php?t=5200
     * 
     * @param element
     * @param tip
     */
    public static void setQuickTip(final Element element, final String tip) {
        // If we don't want to use ext, use 'title' instead (limited to 64
        // chars)
        if (tip == null || tip.length() == 0) {
            DOM.removeElementAttribute(element, "ext:qtip");
        } else {
            // check size here?
            String newTip = "<div style='min-width: 35px'>" + tip + "</div>";
            DOM.setElementAttribute(element, "ext:qtip", newTip);
        }
    }

    public static void setQuickTip(final Element element, final String tip, final String tipTitle) {
        setQuickTip(element, tip);
        DOM.setElementAttribute(element, "ext:qtitle", tipTitle);
    }

}
