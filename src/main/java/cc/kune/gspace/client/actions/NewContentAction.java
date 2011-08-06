package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewContentAction extends RolAction {

  public static final String ID = "ctnnewid";
  public static final String NEW_NAME = "ctnnewname";

  private final ContentCache cache;
  private final Provider<ContentServiceAsync> contentService;
  private final ContentViewerPresenter contentViewer;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public NewContentAction(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService,
      final ContentViewerPresenter contentViewerPresenter, final ContentCache cache) {
    super(AccessRolDTO.Editor, true);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.contentService = contentService;
    this.contentViewer = contentViewerPresenter;
    this.cache = cache;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.showProgressProcessing();
    // stateManager.gotoStateToken(((HasContent)
    // session.getCurrentState()).getContainer().getStateToken());
    final String newName = (String) getValue(NEW_NAME);
    contentService.get().addContent(session.getUserHash(),
        session.getCurrentStateToken().copy().clearDocument(), newName, (String) getValue(ID),
        new AsyncCallbackSimple<StateContentDTO>() {
          @Override
          public void onSuccess(final StateContentDTO state) {
            stateManager.setRetrievedStateAndGo(state);
            NotifyUser.hideProgress();
            // stateManager.refreshCurrentGroupState();
            // contextNavigator.setEditOnNextStateChange(true);
            NotifyUser.info(i18n.tWithNT("[%s] created", "New content created, for instance", newName));
            contentViewer.blinkTitle();
          }
        });
    cache.removeContent(session.getCurrentStateToken());
  }
}
