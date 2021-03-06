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
package cc.kune.polymer.client.actions.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import br.com.rpa.client._ironelements.HasIcon;
import br.com.rpa.client._ironelements.IronIcon;
import br.com.rpa.client._paperelements.PaperButton;
import br.com.rpa.client._paperelements.PaperCustomButton;
import br.com.rpa.client._paperelements.PaperIconButton;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;

/**
 * The Class AbstractPoButtonGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractPoPaperButtonGui extends AbstractPoChildGuiItem implements HasIcon {

  /** The button. */
  private PaperButton button;
  /** The enable tongle. */
  protected boolean enableTongle;
  private boolean hasIcon;
  private boolean hasText;
  private HasIcon icon;
  private Widget iconWidget;

  /**
   * Instantiates a new abstract gwt button gui.
   */
  public AbstractPoPaperButtonGui() {
    this(null, false);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param enableTongle
   *          the enable tongle
   */
  public AbstractPoPaperButtonGui(final boolean enableTongle) {
    this(null, enableTongle);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param buttonDescriptor
   *          the button descriptor
   */
  public AbstractPoPaperButtonGui(final ButtonDescriptor buttonDescriptor) {
    this(buttonDescriptor, false);
  }

  /**
   * Instantiates a new abstract gwt button gui.
   *
   * @param buttonDescriptor
   *          the button descriptor
   * @param enableTongle
   *          the enable tongle
   */
  public AbstractPoPaperButtonGui(final ButtonDescriptor buttonDescriptor, final boolean enableTongle) {
    super(buttonDescriptor);
    this.enableTongle = enableTongle;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractChildGuiItem#addStyle(java.lang
   * .String)
   */
  @Override
  protected void addStyle(final String style) {
    button.addStyleName(style);
  }

  private PaperIconButton castIcon() {
    return (PaperIconButton) icon;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractChildGuiItem#create(cc.kune.common
   * .client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;

    hasIcon = descriptor.hasIcon();
    hasText = descriptor.hasText();
    final String id = descriptor.getId();

    if (hasIcon && hasText) {
      icon = new IronIcon();
      iconWidget = (Widget) icon;
      button = new PaperCustomButton<Widget>((IronIcon) icon);
      button.setToggles(enableTongle);
      descriptor.putValue(ICON, icon);
      endConfigureBtn(descriptor, id);
    } else if (hasText) {
      button = new PaperButton();
      button.setToggles(enableTongle);
      endConfigureBtn(descriptor, id);
    } else if (hasIcon) {
      icon = new PaperIconButton();
      iconWidget = (Widget) icon;
      descriptor.putValue(ICON, icon);
      endConfigureIcon(descriptor, id);
    }

    final String value = (String) descriptor.getValue(Action.STYLES);
    if (value != null) {
      setStyles(value);
    }
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  private void endConfigureBtn(final GuiActionDescrip descriptor, final String id) {
    if (id != null) {
      button.ensureDebugId(id);
    }
    if (descriptor.isChild()) {
      // If button is inside a toolbar don't init...
      child = button;
    } else {
      initWidget(button);
    }
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        descriptor.fire(new ActionEvent(button, getTargetObjectOfAction(descriptor),
            Event.as(event.getNativeEvent())));
      }
    });
  }

  private void endConfigureIcon(final GuiActionDescrip descriptor, final String id) {

    if (id != null) {
      iconWidget.ensureDebugId(id);
    }
    if (descriptor.isChild()) {
      // If button is inside a toolbar don't init...
      child = iconWidget;
    } else {
      initWidget(iconWidget);
    }
    castIcon().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        descriptor.fire(new ActionEvent(iconWidget, getTargetObjectOfAction(descriptor),
            Event.as(event.getNativeEvent())));
      }
    });
  }

  @Override
  public String getIcon() {
    return icon.getIcon();
  }

  @Override
  public String getIconSrc() {
    return icon.getIconSrc();
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    if (hasText) {
      button.setEnabled(enabled);
    } else {
      castIcon().setEnabled(enabled);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common
   * .shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    // FIXME
    // Not implemented
    // button.setIcon(icon);
  }

  @Override
  public void setIcon(final String iconS) {
    if (hasIcon) {
      icon.setIcon(iconS);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackColor(final String backgroundColor) {
    if (hasIcon) {
      iconWidget.getElement().getStyle().setBackgroundColor(backgroundColor);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google
   * .gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource image) {
    if (hasIcon) {
      icon.setIconSrc(image.getSafeUri().asString());
    }
  }

  @Override
  public void setIconSrc(final String iconSrc) {
    if (hasIcon) {
      icon.setIconSrc(iconSrc);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang
   * .String)
   */
  @Override
  protected void setIconStyle(final String style) {
    if (hasIcon) {
      iconWidget.setStyleName(style);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String
   * )
   */
  @Override
  public void setIconUrl(final String url) {
    if (hasIcon) {
      icon.setIconSrc(url);
    }
  }

  /**
   * Sets the pressed.
   *
   * @param pressed
   *          the new pressed
   */
  public void setPressed(final boolean pressed) {
    if (hasText) {
      button.setActive(pressed);
    } else {
      castIcon().setActive(pressed);
    }
  }

  @Override
  protected void setStyles(final String styles) {
    // TODO Auto-generated method stub
    // Only to see it the problem is here
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    // FIXME descriptor.getDirection()
    if (hasText) {
      button.setText(text);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang
   * .String)
   */
  @Override
  public void setToolTipText(final String tooltipText) {
    if (hasText) {
      Tooltip.to(button, tooltipText);
    } else {
      Tooltip.to(iconWidget, tooltipText);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    if (hasText) {
      button.setVisible(visible);
    } else {
      iconWidget.setVisible(visible);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }

}
