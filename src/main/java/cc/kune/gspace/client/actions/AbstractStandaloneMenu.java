/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;
import cc.kune.core.client.events.AccessRightsChangedEvent;
import cc.kune.core.client.events.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractStandaloneMenu.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractStandaloneMenu extends WidgetMenuDescriptor {

  /**
   * Instantiates a new abstract standalone menu.
   *
   * @param rightsManager
   *          the rights manager
   */
  @Inject
  public AbstractStandaloneMenu(final AccessRightsClientManager rightsManager) {
    setStandalone(true);
    // We set a dummy widget
    setWidget(new InlineLabel());
    this.withStyles(ActionStyles.MENU_BTN_STYLE_LEFT);
    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        AbstractStandaloneMenu.this.setVisible(event.getCurrentRights().isEditable());
      }
    });

  }
}
