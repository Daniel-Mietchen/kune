package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TimeZoneDTO;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.errors.EmailAddressInUseException;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;

import com.calclab.emiteuimodule.client.SubscriptionMode;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RegisterPresenter extends SignInAbstractPresenter implements Register {

    private RegisterView view;
    private final Provider<UserServiceAsync> userServiceProvider;
    private final Provider<SignIn> signInProvider;

    public RegisterPresenter(Session session, StateManager stateManager, I18nUITranslationService i18n,
            Provider<UserServiceAsync> userServiceProvider, Provider<SignIn> signInProvider) {
        super(session, stateManager, i18n);
        this.userServiceProvider = userServiceProvider;
        this.signInProvider = signInProvider;
    }

    public void doRegister(StateToken previousStateToken) {
        this.previousStateToken = previousStateToken;
        signInProvider.get().hide();
        if (!session.isLogged()) {
            Site.showProgressProcessing();
            view.show();
            view.center();
            Site.hideProgress();
        } else {
            stateManager.gotoToken(previousStateToken);
        }
    }

    public View getView() {
        return view;
    }

    public void init(RegisterView view) {
        this.view = view;
        super.view = view;
    }

    public void onFormRegister() {
        if (view.isRegisterFormValid()) {
            view.maskProcessing();

            final I18nLanguageDTO language = new I18nLanguageDTO();
            language.setCode(view.getLanguage());

            final I18nCountryDTO country = new I18nCountryDTO();
            country.setCode(view.getCountry());

            final TimeZoneDTO timezone = new TimeZoneDTO();
            timezone.setId(view.getTimezone());

            boolean wantPersonalHomepage = view.wantPersonalHomepage();

            final UserDTO user = new UserDTO(view.getLongName(), view.getShortName(), view.getRegisterPassword(),
                    view.getEmail(), language, country, timezone, null, true, SubscriptionMode.manual, "blue");
            final AsyncCallback<UserInfoDTO> callback = new AsyncCallback<UserInfoDTO>() {
                public void onFailure(final Throwable caught) {
                    view.unMask();
                    try {
                        throw caught;
                    } catch (final EmailAddressInUseException e) {
                        view.setErrorMessage(i18n.t("This email in in use by other person, try with another."),
                                SiteErrorType.error);
                    } catch (final GroupNameInUseException e) {
                        view.setErrorMessage(i18n.t("This name in already in use, try with a different name."),
                                SiteErrorType.error);
                    } catch (final Throwable e) {
                        view.setErrorMessage(i18n.t("Error during registration."), SiteErrorType.error);
                        GWT.log("Other kind of exception in user registration" + e.getMessage() + ", "
                                + e.getLocalizedMessage(), null);
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }

                public void onSuccess(final UserInfoDTO userInfoDTO) {
                    stateManager.gotoToken(userInfoDTO.getHomePage());
                    onSignIn(userInfoDTO);
                    view.hide();
                    view.unMask();
                    view.showWelcolmeDialog();
                }
            };
            userServiceProvider.get().createUser(user, wantPersonalHomepage, callback);
        }
    }
}