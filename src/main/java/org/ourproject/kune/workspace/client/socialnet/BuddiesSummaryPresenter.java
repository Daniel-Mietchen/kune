/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;
import org.ourproject.kune.platf.client.actions.RadioMustBeChecked;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDataDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserBuddiesVisibilityDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.state.AccessRightsClientManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.socialnet.toolbar.ActionBuddiesSummaryToolbar;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class BuddiesSummaryPresenter extends SocialNetworkPresenter implements BuddiesSummary {

    public static final String BUDDIES_VISIBILITY_GROUP = "k-bsp-bud-visib";

    private BuddiesSummaryView view;
    private final StateManager stateManager;
    private final UserActionRegistry actionRegistry;
    private final I18nTranslationService i18n;
    private final Provider<ChatEngine> chatEngineProvider;
    private final Session session;
    private final ActionBuddiesSummaryToolbar toolbar;
    private final Provider<UserServiceAsync> userServiceAsync;
    private final Provider<FileDownloadUtils> fileDownUtilsProvider;

    public BuddiesSummaryPresenter(final StateManager stateManager, final Session session,
            final Provider<UserServiceAsync> userServiceAsync, final UserActionRegistry actionRegistry,
            final I18nTranslationService i18n, final Provider<ChatEngine> chatEngineProvider,
            final ActionBuddiesSummaryToolbar toolbar, final Provider<FileDownloadUtils> fileDownUtilsProvider,
            final ImageUtils imageUtils, final Provider<SocialNetworkServiceAsync> snServiceAsync,
            final GroupActionRegistry groupActionRegistry, final AccessRightsClientManager accessRightManager,
            final ImgResources img) {
        super(i18n, stateManager, accessRightManager, session, snServiceAsync, groupActionRegistry,
                fileDownUtilsProvider, img);
        this.stateManager = stateManager;
        this.session = session;
        this.userServiceAsync = userServiceAsync;
        this.actionRegistry = actionRegistry;
        this.i18n = i18n;
        this.chatEngineProvider = chatEngineProvider;
        this.toolbar = toolbar;
        this.fileDownUtilsProvider = fileDownUtilsProvider;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
        stateManager.onSocialNetworkChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
        registerActions();
    }

    public View getView() {
        return view;
    }

    public void init(final BuddiesSummaryView view) {
        this.view = view;
    }

    protected void setState(final StateAbstractDTO state) {
        if (state.getGroup().isPersonal()) {
            view.clear();
            final UserBuddiesDataDTO userBuddies = state.getUserBuddies();
            if (state.getSocialNetworkData().isBuddiesVisible()) {
                final List<UserSimpleDTO> buddies = userBuddies.getBuddies();
                for (final UserSimpleDTO user : buddies) {
                    final String avatarUrl = user.hasLogo() ? fileDownUtilsProvider.get().getLogoImageUrl(
                            user.getStateToken()) : BuddiesSummaryView.NOAVATAR;
                    final String tooltip = super.createTooltipWithLogo(user.getShortName(), user.getStateToken(),
                            user.hasLogo(), true);
                    view.addBuddie(user, actionRegistry.getCurrentActions(user, session.isLogged(),
                            new AccessRightsDTO(true, true, true), false), avatarUrl, user.getName(), tooltip);
                }
                final boolean hasLocalBuddies = buddies.size() > 0;
                final int numExtBuddies = userBuddies.getOtherExtBuddies();
                if (numExtBuddies > 0) {
                    if (hasLocalBuddies) {
                        // i18n: plural
                        view.setOtherUsers(i18n.t(numExtBuddies == 1 ? "and [%d] external user"
                                : "and [%d] external users", numExtBuddies));
                    } else {
                        view.setOtherUsers(i18n.t(numExtBuddies == 1 ? "[%d] external user" : "[%d] external users",
                                numExtBuddies));
                    }
                } else {
                    if (hasLocalBuddies) {
                        view.clearOtherUsers();
                    } else {
                        view.setNoBuddies();
                    }
                }
                toolbar.disableMenusAndClearButtons();
                toolbar.addActions(actionRegistry.getCurrentActions(session.getCurrentUser(), session.isLogged(),
                        state.getGroupRights(), true), ActionToolbar.IN_ANY);
                toolbar.attach();
                view.show();
            } else {
                view.showBuddiesNotVisible();
                view.show();
            }
        } else {
            view.hide();
        }
    }

    private void createAddNewBuddiesAction() {
        final ActionToolbarMenuDescriptor<UserSimpleDTO> addNewBuddiesAction = new ActionToolbarMenuDescriptor<UserSimpleDTO>(
                AccessRolDTO.Administrator, buddiesBottom, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO parameter) {
                        NotifyUser.info("In development");
                    }
                });
        addNewBuddiesAction.setTextDescription(i18n.t("Add a new buddy"));
        addNewBuddiesAction.setParentMenuTitle(i18n.t("Options"));
        addNewBuddiesAction.setIconUrl("images/add-green.gif");
        actionRegistry.addAction(addNewBuddiesAction);
    }

    private void createSetBuddiesVisibilityAction(final String textDescription,
            final UserBuddiesVisibilityDTO visibility) {
        final ActionToolbarMenuRadioDescriptor<UserSimpleDTO> buddiesVisibilityAction = new ActionToolbarMenuRadioDescriptor<UserSimpleDTO>(
                AccessRolDTO.Administrator, buddiesBottom, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO parameter) {
                        userServiceAsync.get().setBuddiesVisibility(session.getUserHash(),
                                session.getCurrentState().getGroup().getStateToken(), visibility,
                                new AsyncCallbackSimple<Void>() {
                                    public void onSuccess(final Void result) {
                                        NotifyUser.info(i18n.t("Buddies visibility changed"));
                                    }
                                });
                    }
                }, BUDDIES_VISIBILITY_GROUP, new RadioMustBeChecked() {
                    public boolean mustBeChecked() {
                        final StateAbstractDTO currentState = session.getCurrentState();
                        if (currentState.getGroup().isPersonal()) {
                            final SocialNetworkDataDTO socialNetworkData = currentState.getSocialNetworkData();
                            return socialNetworkData.getUserBuddiesVisibility().equals(visibility);
                        }
                        return false;
                    }
                });
        buddiesVisibilityAction.setTextDescription(textDescription);
        buddiesVisibilityAction.setParentMenuTitle(i18n.t("Options"));
        buddiesVisibilityAction.setParentSubMenuTitle(i18n.t("Those who can view your buddies list"));
        actionRegistry.addAction(buddiesVisibilityAction);
    }

    private void registerActions() {
        final ActionMenuItemDescriptor<UserSimpleDTO> addAsBuddie = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO user) {
                        chatEngineProvider.get().addNewBuddie(user.getShortName());
                    }
                });
        addAsBuddie.setMustBeAuthenticated(true);
        addAsBuddie.setTextDescription(i18n.t("Add as a buddie"));
        addAsBuddie.setIconUrl("images/add-green.png");
        addAsBuddie.setEnableCondition(new ActionEnableCondition<UserSimpleDTO>() {
            public boolean mustBeEnabled(final UserSimpleDTO user) {
                return !chatEngineProvider.get().isBuddie(user.getShortName());
            }
        });
        actionRegistry.addAction(addAsBuddie);

        final ActionMenuItemDescriptor<UserSimpleDTO> go = new ActionMenuItemDescriptor<UserSimpleDTO>(
                AccessRolDTO.Viewer, new Listener<UserSimpleDTO>() {
                    public void onEvent(final UserSimpleDTO user) {
                        stateManager.gotoToken(user.getShortName());
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Visit this user's homepage"));
        go.setIconUrl("images/group-home.gif");
        actionRegistry.addAction(go);

        createAddNewBuddiesAction();
        createSetBuddiesVisibilityAction(i18n.t("anyone"), UserBuddiesVisibilityDTO.anyone);
        createSetBuddiesVisibilityAction(i18n.t("only your buddies"), UserBuddiesVisibilityDTO.yourbuddies);
        createSetBuddiesVisibilityAction(i18n.t("only you"), UserBuddiesVisibilityDTO.onlyyou);
    }

}
