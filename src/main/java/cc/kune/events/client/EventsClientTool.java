/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.events.client;

import static cc.kune.events.shared.EventsToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class EventsClientTool extends FoldableAbstractClientTool {

  @Inject
  public EventsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(TOOL_NAME, i18n.t(ROOT_NAME), i18n.t("A calendar to schedule activities and events"),
        ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerAuthorableTypes(TYPE_MEETING);
    registerDragableTypes(TYPE_MEETING);
    registerDropableTypes(TYPE_ROOT);
    // registerPublishModerableTypes();
    registerRateableTypes(TYPE_MEETING);
    registerRenamableTypes(TYPE_MEETING);
    registerTageableTypes(TYPE_MEETING);
    registerTranslatableTypes(TYPE_MEETING);

    registerIcons();
  }

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_ROOT);
    registerContentTypeIcon(TYPE_MEETING, ICON_TYPE_MEETING);
    registerEmptyMessages(TYPE_ROOT, i18n.t("There isn't any meeting, you can create one"));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t("There isn't any meeting"));
  }
}
