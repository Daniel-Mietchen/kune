/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

package cc.kune.gspace.client.armor;

import static cc.kune.polymer.client.PolymerId.*;

import java.util.HashMap;

import br.com.rpa.client._coreelements.CoreIconButton;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.FlowActionExtensible;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.HTMLId;
import cc.kune.common.client.ui.WrappedFlowPanel;
import cc.kune.polymer.client.PolymerUtils;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GSpaceArmorPolymer implements GSpaceArmor {

  private static Element getElementById(final String id) {
    return DOM.getElementById(id);
  }

  private native static Element getShadowElement(String father, String child) /*-{
		return $doc.querySelector(father).shadowRoot.querySelector(child)
  }-*/;

  private static boolean isElementChildOfWidget(Element element) {
    // Walk up the DOM hierarchy, looking for any widget with an event listener
    // set. Though it is not dependable in the general case that a widget will
    // have set its element's event listener at all times, it *is* dependable
    // if the widget is attached. Which it will be in this case.
    element = element.getParentElement();
    final BodyElement body = Document.get().getBody();
    while ((element != null) && (body != element)) {
      if (Event.getEventListener(element) != null) {
        Log.error("Element: " + element + " already wrapped/attached");
        return true;
      }
      element = element.getParentElement().cast();
    }
    return false;
  }

  private final GSpaceCenter centerPanel;
  private final ActionFlowPanel docFooterToolbar;
  private final HashMap<String, Element> elements;
  private final ActionFlowPanel entityFooterToolbar;
  private final FlowActionExtensible flowActionTrash;
  private final ActionFlowPanel headerToolbar;
  private final HashMap<String, WrappedFlowPanel> panels;
  private final ActionFlowPanel subheaderToolbar;
  private final ActionFlowPanel toolsSouthToolbar;
  private final FlowPanel trash;

  @Inject
  GSpaceArmorPolymer(final GSpaceCenter centerPanel, final Provider<ActionFlowPanel> toolbarProv) {
    panels = new HashMap<String, WrappedFlowPanel>();
    elements = new HashMap<String, Element>();

    // This (temporal) panel is for not already implemented panels, so we can
    // continue without showing this widgets
    // Should be deleted when finished
    trash = new FlowPanel();
    flowActionTrash = new FlowActionExtensible();
    trash.add(flowActionTrash);

    docFooterToolbar = toolbarProv.get();
    headerToolbar = toolbarProv.get();
    subheaderToolbar = toolbarProv.get();
    toolsSouthToolbar = toolbarProv.get();
    entityFooterToolbar = toolbarProv.get();

    PolymerUtils.addFlexHorLayout(docFooterToolbar, subheaderToolbar, toolsSouthToolbar,
        entityFooterToolbar);
    PolymerUtils.addFlexVerLayout(headerToolbar);

    getEntityHeader().add(headerToolbar);
    getDocFooter().add(docFooterToolbar);
    getEntityToolsSouth().add(toolsSouthToolbar);
    getEntityFooter().add(entityFooterToolbar);
    getDocSubheader().add(subheaderToolbar);

    this.centerPanel = centerPanel;
    wrapDiv(DOC_CONTENT).add(centerPanel);
  }

  @Override
  public void clearBackImage() {
    setBackImage("none !important");
  }

  private SimplePanel createDummySimplePanel() {
    final SimplePanel panel = new SimplePanel();
    trash.add(panel);
    return panel;
  }

  @Override
  public void enableCenterScroll(final boolean enable) {
    try {
      getElementById("doc_content_section").getStyle().setOverflowY(
          enable ? Overflow.AUTO : Overflow.HIDDEN);
    } catch (final Exception e) {
      Log.error("Cannot set scroll in center panel");
    }
  }

  @Override
  public GSpaceCenter getDocContainer() {
    return centerPanel;
  }

  @Override
  public int getDocContainerHeight() {
    return wrapDiv(DOC_CONTENT).getOffsetHeight();
  }

  @Override
  public ForIsWidget getDocFooter() {
    return wrapDiv(MIGA);
  }

  @Override
  public IsActionExtensible getDocFooterToolbar() {
    return docFooterToolbar;
  }

  @Override
  public ForIsWidget getDocHeader() {
    return wrapDiv(DOC_HEADER);
  }

  @Override
  public ForIsWidget getDocSubheader() {
    return wrapDiv(DOC_TOOLBAR_EXTENSION);
  }

  @Override
  public Element getElement(final HTMLId htmlId) {
    final String id = htmlId.getId();
    Element element = elements.get(id);
    if (element == null) {
      element = getElementById(id);
      elements.put(id, element);
    }
    return element;
  }

  @Override
  public ForIsWidget getEntityFooter() {
    return wrapDiv(SITE_BOTTOMBAR);
  }

  @Override
  public IsActionExtensible getEntityFooterToolbar() {
    return flowActionTrash;
  }

  @Override
  public ForIsWidget getEntityHeader() {
    return wrapDiv(GROUP_ENTITY_TOOLBAR);
  }

  @Override
  public ForIsWidget getEntityToolsCenter() {
    return trash;
  }

  @Override
  public ForIsWidget getEntityToolsNorth() {
    return wrapDiv(HEADER_SOCIAL_NET);
  }

  @Override
  public ForIsWidget getEntityToolsSouth() {
    return trash;
  }

  @Override
  public ButtonBase getFollowersButton() {
    return CoreIconButton.wrap(GROUP_FOLLOWERS.getId());
  }

  @Override
  public Image getGroupLogo() {
    return Image.wrap(getElement(HEADER_GROUP_LOGO));
  }

  @Override
  public WrappedFlowPanel getGroupName() {
    return wrapDiv(HEADER_GROUP_NAME);
  }

  @Override
  public WrappedFlowPanel getGroupShortName() {
    return wrapDiv(HEADER_SHORT_GROUP_NAME);
  }

  @Override
  public IsActionExtensible getHeaderToolbar() {
    return headerToolbar;
  }

  @Override
  public SimplePanel getHomeSpace() {
    return createDummySimplePanel();
  }

  public ForIsWidget getHomeSpaceFlow() {
    return wrapDiv(HOME_CENTER);
  }

  @Override
  public Element getLogoShadow() {
    return getElement(HEADER_GROUP_SHADOW);
  }

  @Override
  public IsWidget getMainpanel() {
    return wrapDiv(GROUP_SPACE);
  }

  @Override
  public SimplePanel getPublicSpace() {
    return createDummySimplePanel();
  }

  @Override
  public ForIsWidget getSitebarLeft() {
    return wrapDiv(SITEBAR_LEFT_EXTENSIONBAR);
  }

  @Override
  public ForIsWidget getSitebarRight() {
    return wrapDiv(SITEBAR_RIGHT_EXTENSIONBAR);
  }

  private void getSpace(final int index) {
    getElement(SPACE_SELECTOR).setPropertyInt("selected", index);
  }

  @Override
  public IsActionExtensible getSubheaderToolbar() {
    return subheaderToolbar;
  }

  @Override
  public IsActionExtensible getToolsSouthToolbar() {
    return toolsSouthToolbar;
  }

  @Override
  public ForIsWidget getUserSpace() {
    return wrapDiv(USER_SPACE);
  }

  @Override
  public void selectGroupSpace() {
    getSpace(2);
  }

  @Override
  public void selectHomeSpace() {
    getSpace(0);
  }

  @Override
  public void selectPublicSpace() {
    getSpace(2);
  }

  @Override
  public void selectUserSpace() {
    getSpace(1);
  }

  @Override
  // TODO avoid querySelector
  public native void setBackImage(final String url) /*-{
		$doc.querySelector('#kunetemplate').group_back_image_url = url;
  }-*/;

  @Override
  public void setMaximized(final boolean maximized) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setRTL(final Direction direction) {
    // TODO use reverse methods in Polymer also
    // http://stackoverflow.com/questions/26110405/polymer-rtl-text-based-on-an-attribute
  }

  @Override
  public WrappedFlowPanel wrapDiv(final HTMLId htmlId) {
    final String id = htmlId.getId();
    WrappedFlowPanel panel = panels.get(id);
    if (panel == null) {
      Log.debug("Getting div '" + id + "' from html");
      final Element element = getElement(htmlId);
      if (isElementChildOfWidget(element)) {
        throw new UIException("Parent is already wrapped/attached");
      }
      panel = WrappedFlowPanel.wrap(element);
      PolymerUtils.addFlexHorLayout(panel);
      assert panel != null;
      panels.put(id, panel);
    }
    return panel;
  }

}
