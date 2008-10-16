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
package org.ourproject.kune.workspace.client.signin;

import java.util.Date;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.dialogs.InfoDialog;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.msg.SimpleMessagePanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.layout.FitLayout;

public class SignInPanel implements SignInView {
    private class MessagePanel extends Panel {
        private static final String SIGNIN_MESSAGE_PANEL = "kune-sip-mp";
        private final SimpleMessagePanel messagesPanel;

        public MessagePanel() {
            setPaddings(10);
            setBorder(false);
            setHeight(60);
            messagesPanel = new SimpleMessagePanel();
            messagesPanel.ensureDebugId(SIGNIN_MESSAGE_PANEL);
            messagesPanel.setMessage("", SiteErrorType.info, SiteErrorType.error);
            add(messagesPanel);
        }

        @Override
        public void hide() {
            messagesPanel.hide();
            super.hide();
        }

        public void setMessage(final String message, final SiteErrorType lastMessageType, final SiteErrorType type) {
            messagesPanel.setMessage(message, lastMessageType, type);
        }

        @Override
        public void show() {
            messagesPanel.show();
            super.show();
        }
    }

    private static final String USER_SIGN_IN_PANEL = "kune-sip-usp";
    private static final String USER_REGISTER_PANEL = "kune-sip-rp";
    private static final String CANCEL_BUTTON_ID = "kune-sip-cb";
    private static final String REGISTER_BUTTON_ID = "kune-sip-rb";
    private static final String SIGN_IN_BUTTON_ID = "kune-sip-sib";

    private BasicDialog dialog;
    private final SignInPresenter presenter;
    private SignInForm signInForm;
    private RegisterForm registerForm;
    private MessagePanel messagesSignInPanel;
    private InfoDialog welcomeDialog;
    private TabPanel centerPanel;
    private MessagePanel messagesRegisterPanel;
    private final I18nUITranslationService i18n;
    private Label errorLabel;

    public SignInPanel(final SignInPresenter presenter, final I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
        this.i18n = i18n;
        Field.setMsgTarget("side");
        this.presenter = presenter;
        createPanel();
    }

    public void center() {
        dialog.center();
    }

    public String getCountry() {
        return registerForm.getCountry();
    }

    public String getEmail() {
        return registerForm.getEmail();
    }

    public String getLanguage() {
        return registerForm.getLanguage();
    }

    public String getLoginPassword() {
        return signInForm.getLoginPassword();
    }

    public String getLongName() {
        return registerForm.getLongName();
    }

    public String getNickOrEmail() {
        return signInForm.getNickOrEmail();
    }

    public String getRegisterPassword() {
        return registerForm.getRegisterPassword();
    }

    public String getRegisterPasswordDup() {
        return registerForm.getRegisterPasswordDup();
    }

    public String getShortName() {
        return registerForm.getShortName();
    }

    public String getTimezone() {
        return registerForm.getTimezone();
    }

    public void hide() {
        dialog.hide();
    }

    public void hideMessages() {
        errorLabel.setText("");
        messagesSignInPanel.hide();
        if (messagesRegisterPanel != null) {
            messagesRegisterPanel.hide();
        }
        renderDialogIfNeeded();
    }

    public boolean isRegisterFormValid() {
        return registerForm.isValid();
    }

    public boolean isSignInFormValid() {
        return signInForm.isValid();
    }

    public void mask(final String message) {
        dialog.getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void reset() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                signInForm.reset();
                if (registerForm != null) {
                    registerForm.reset();
                }
            }
        });
    }

    public void setCookie(final String userHash) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        final long duration = Session.SESSION_DURATION;
        final Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie(Site.USERHASH, userHash, expires, null, "/", false);
        GWT.log("Received hash: " + userHash, null);
    }

    public void setRegisterMessage(final String message, final SiteErrorType type) {
        messagesRegisterPanel.setMessage(message, type, type);
        messagesRegisterPanel.show();
        renderDialogIfNeeded();
    }

    public void setSignInMessage(final String message, final SiteErrorType type) {
        errorLabel.setText(message);
        messagesSignInPanel.setMessage(message, type, type);
        messagesSignInPanel.show();
        renderDialogIfNeeded();
    }

    public void show() {
        centerPanel.activate(0);
        dialog.setVisible(true);
        dialog.show();
        Site.hideProgress();
        dialog.focus();
        signInForm.focusLogin();
    }

    public void showWelcolmeDialog() {
        if (welcomeDialog == null) {
            welcomeDialog = new InfoDialog(i18n.t("Welcome"), i18n.t("Thanks for registering"), i18n
                    .t("Now you can participate more actively in this site with other people and groups. "
                            + "You can also use your personal space to publish contents. "
                            + "Your email is not verified, please follow the instructions you will receive by email."),
                    i18n.t("Ok"), true, true, 400, 270);
        }
        welcomeDialog.show();
    }

    public void unMask() {
        dialog.getEl().unmask();
    }

    public boolean wantPersonalHomepage() {
        return registerForm.wantPersonalHomepage();
    }

    private void confPanel(final Panel panel) {
        // panel.setLayout(new FormLayout());
        // panel.setAutoWidth(true);
        // anel.setAutoHeight(true);
        panel.setAutoScroll(true);
    }

    private Panel createNoAccountRegister() {
        final Panel noAccRegisterPanel = new Panel();
        noAccRegisterPanel.setBorder(false);
        noAccRegisterPanel.setMargins(0, 20, 0, 0);
        HorizontalPanel hp = new HorizontalPanel();
        final Label dontHaveAccountLabel = new Label(i18n.t("Don't have an account?"));
        final Label registerLabel = new Label(i18n.t("Create one."));
        registerLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                centerPanel.activate(1);
            }
        });
        registerLabel.addStyleName("kune-Margin-Medium-l");
        registerLabel.addStyleName("kune-link");
        hp.add(dontHaveAccountLabel);
        hp.add(registerLabel);
        noAccRegisterPanel.add(hp);
        return noAccRegisterPanel;
    }

    private void createPanel() {
        dialog = new BasicDialog(i18n.t("Sign in"), true, false, 370, 400);
        dialog.setLayout(new FitLayout());
        final Panel dialogPanel = new Panel();
        dialogPanel.setLayout(new FitLayout());
        dialogPanel.setBorder(false);
        // dialog.setAutoHeight(false);
        // dialog.setAutoWidth(false);
        // dialog.setAutoHeight(true);
        // dialog.setAutoWidth(true);
        dialogPanel.setHeight("100%");
        dialogPanel.setWidth("auto");
        // dialog.setCollapsible(false);

        centerPanel = new TabPanel();
        centerPanel.setBorder(false);
        centerPanel.setActiveTab(0);
        // centerPanel.setAutoWidth(true);
        // centerPanel.setAutoHeight(true);
        centerPanel.setClosable(false);

        final Panel signInPanel = new Panel(i18n.t("Sign in"));
        signInPanel.setBorder(false);
        signInPanel.setMargins(20);
        confPanel(signInPanel);
        signInForm = new SignInForm(i18n);
        signInPanel.add(signInForm.getForm());
        signInPanel.setCls(USER_SIGN_IN_PANEL);
        signInPanel.add(createNoAccountRegister());
        messagesSignInPanel = new MessagePanel();
        signInPanel.add(messagesSignInPanel);

        final Panel registerPanel = new Panel(i18n.t("Register"));
        signInPanel.setBorder(false);
        registerPanel.setCls(USER_REGISTER_PANEL);
        confPanel(registerPanel);

        centerPanel.add(signInPanel);
        centerPanel.add(registerPanel);
        dialogPanel.add(centerPanel);
        errorLabel = new Label("");
        Toolbar messageToolbar = new Toolbar();
        messageToolbar.addElement(errorLabel.getElement());
        dialog.setBottomToolbar(messageToolbar);
        dialog.add(dialogPanel);

        final Button signInBtn = new Button(i18n.t("Sign in"));
        signInBtn.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                signInForm.validate();
                if (signInForm.isValid()) {
                    presenter.onFormSignIn();
                }
            }
        });
        signInBtn.setId(SIGN_IN_BUTTON_ID);
        dialog.addButton(signInBtn);

        final Button registerBtn = new Button(i18n.t("Register"));
        registerBtn.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                registerForm.validate();
                if (registerForm.isValid()) {
                    presenter.onFormRegister();
                }
            }
        });
        dialog.addButton(registerBtn);
        registerBtn.setId(REGISTER_BUTTON_ID);
        registerBtn.hide();

        final Button cancel = new Button();
        cancel.setId(CANCEL_BUTTON_ID);
        dialog.addButton(cancel);
        cancel.setText(i18n.tWithNT("Cancel", "used in button"));
        cancel.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                presenter.onCancel();
            }
        });

        signInPanel.addListener(new PanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                dialog.setTitle(i18n.t("Sign in"));
                registerBtn.hide();
                signInBtn.show();
            }
        });

        registerPanel.addListener(new PanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                if (registerForm == null) {
                    maskProcessing();
                    registerForm = new RegisterForm(presenter, i18n);
                    registerPanel.add(registerForm.getForm());
                    messagesRegisterPanel = new MessagePanel();
                    registerPanel.add(messagesRegisterPanel);
                    messagesRegisterPanel.hide();
                    renderDialogIfNeeded();
                    unMask();
                }
                dialog.setTitle(i18n.t("Register"));
                signInBtn.hide();
                registerBtn.show();
            }
        });

        dialog.addListener(new WindowListenerAdapter() {
            @Override
            public void onHide(final Component component) {
                presenter.onClose();
            }
        });
        hideMessages();
        renderDialogIfNeeded();
    }

    private void renderDialogIfNeeded() {
        if (dialog.isRendered()) {
            dialog.doLayout();
        }
    }
}
