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

package org.ourproject.kune.workspace.client.actions;

import org.ourproject.kune.platf.client.Services;
import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DeleteMemberAction implements Action {

    public void execute(final Object value, final Object extra, final Services services) {
        onDeleteMember(services, (String) value);
    }

    private void onDeleteMember(final Services services, final String groupShortName) {
        Site.showProgressProcessing();
        final SocialNetworkServiceAsync server = SocialNetworkService.App.getInstance();
        server.deleteMember(services.session.userHash, groupShortName, services.session.getCurrentState().getGroup()
                .getShortName(), new AsyncCallback() {
            public void onFailure(final Throwable caught) {
                Site.hideProgress();
                services.stateManager.processErrorException(caught);
            }

            public void onSuccess(final Object result) {
                Site.hideProgress();
                // i18n
                Site.info("Member removed");
                // TODO: Reload SocialNetwork info only
                services.stateManager.reloadSocialNetwork();
            }
        });

    }
}
