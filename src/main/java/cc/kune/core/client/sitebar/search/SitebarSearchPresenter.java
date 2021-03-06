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
 \*/
package cc.kune.core.client.sitebar.search;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarSearchPresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSearchPresenter extends
Presenter<SitebarSearchPresenter.SitebarSearchView, SitebarSearchPresenter.SitebarSearchProxy> {

  /**
   * The Interface SitebarSearchProxy.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SitebarSearchProxy extends Proxy<SitebarSearchPresenter> {
  }

  /**
   * The Interface SitebarSearchView.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SitebarSearchView extends View {

    /**
     * Clear search text.
     */
    void clearSearchText();

    /**
     * Focus.
     */
    void focus();

    /**
     * Gets the button.
     *
     * @return the button
     */
    HasClickHandlers getButton();

    /**
     * Gets the focus.
     *
     * @return the focus
     */
    HasAllFocusHandlers getFocus();

    /**
     * Gets the key handler.
     *
     * @return the key handler
     */
    HasAllKeyHandlers getKeyHandler();

    /**
     * Gets the text box.
     *
     * @return the text box
     */
    HasText getTextBox();

    /**
     * Select search text.
     */
    void selectSearchText();

    /**
     * Sets the text search.
     *
     * @param text
     *          the new text search
     */
    void setTextSearch(String text);

    void toggleSearch();
  }

  /**
   * Instantiates a new sitebar search presenter.
   *
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   */
  @Inject
  public SitebarSearchPresenter(final EventBus eventBus, final SitebarSearchView view,
      final SitebarSearchProxy proxy, final I18nTranslationService i18n) {
    super(eventBus, view, proxy);

  }

  /**
   * Do search.
   */
  private void doSearch() {
  }

  /**
   * Focus.
   */
  public void focus() {
    getView().focus();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();
    getView().getButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        doSearch();
      }
    });
    getView().getKeyHandler().addKeyUpHandler(new KeyUpHandler() {
      @Override
      public void onKeyUp(final KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          if (getView().getTextBox().getText().length() > 0) {
            NotifyUser.showProgressSearching();
            doSearch();
          }
        }
      }
    });
  }

  /**
   * On search focus.
   */
  public void onSearchFocus() {
    // getView().toggleSearch();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

}
