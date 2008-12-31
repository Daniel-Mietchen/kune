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
package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.platf.client.ui.WindowUtils;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateToken implements IsSerializable {
    public static final String SEPARATOR = ".";
    private static final String[] EMPTY = new String[0];

    public static String encode(final String group, final String tool, final String folder, final String document) {
        String encoded = "";
        if (group != null) {
            encoded += group;
        }
        if (tool != null) {
            encoded += SEPARATOR + tool;
        }
        if (folder != null) {
            encoded += SEPARATOR + folder;
        }
        if (document != null) {
            encoded += SEPARATOR + document;
        }
        return encoded;
    }
    private String group;
    private String tool;
    private String folder;
    private String document;

    private String encoded;

    public StateToken() {
        this(null, null, null, null);
    }

    public StateToken(final String encoded) {
        parse(encoded);
    }

    public StateToken(final String group, final String tool) {
        this(group, tool, null, null);
    }

    public StateToken(final String group, final String tool, final Long folder) {
        this(group, tool, folder == null ? null : folder.toString(), null);
    }

    public StateToken(final String group, final String tool, final String folder, final String document) {
        this.setGroup(group);
        this.setTool(tool);
        this.setFolder(folder);
        this.setDocument(document);
        encoded = null;
    }

    public StateToken clearDocument() {
        this.document = null;
        encoded = null;
        return this;
    }

    public StateToken clearFolder() {
        this.folder = null;
        encoded = null;
        return this;
    }

    public StateToken copy() {
        return new StateToken(this.getEncoded());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final StateToken other = (StateToken) obj;
        if (getEncoded() == null) {
            if (other.getEncoded() != null) {
                return false;
            }
        } else if (!getEncoded().equals(other.getEncoded())) {
            return false;
        }
        return true;
    }

    public String getDocument() {
        return document;
    }

    public String getEncoded() {
        if (encoded == null) {
            encoded = StateToken.encode(getGroup(), getTool(), getFolder(), getDocument());
        }
        return encoded;
    }

    public String getFolder() {
        return folder;
    }

    public String getGroup() {
        return group;
    }

    public String getPublicUrl() {
        String publicUrl = "http://";

        String separator = "/";

        // FIXME: Maybe get from InitData the site.domain
        String port = WindowUtils.getLocation().getPort();
        publicUrl += WindowUtils.getLocation().getHostName() + (port == null ? "" : ":" + port) + "/public";

        if (group != null) {
            publicUrl += separator + group;
        }
        if (tool != null) {
            publicUrl += separator + tool;
        }
        if (folder != null) {
            publicUrl += separator + folder;
        }
        if (document != null) {
            publicUrl += separator + document;
        }

        return publicUrl;
    }

    public String getTool() {
        return tool;
    }

    public boolean hasAll() {
        return getGroup() != null && getTool() != null && getFolder() != null && getDocument() != null;
    }

    public boolean hasGroup() {
        return getGroup() != null;
    }

    public boolean hasGroupAndTool() {
        return getGroup() != null && getTool() != null;
    }

    public boolean hasGroupToolAndFolder() {
        return getGroup() != null && getTool() != null && getFolder() != null;
    }

    @Override
    public int hashCode() {
        return getEncoded().hashCode();
    }

    public boolean hasNothing() {
        return getGroup() == null && getTool() == null && getFolder() == null && getDocument() == null;
    }

    public boolean hasSameContainer(StateToken currentStateToken) {
        if (copy().clearDocument().getEncoded().equals(currentStateToken.copy().clearDocument().getEncoded())) {
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        return getDocument() != null;
    }

    public StateToken setDocument(final Long document) {
        this.document = document == null ? null : document.toString();
        encoded = null;
        return this;
    }

    public StateToken setDocument(final String document) {
        this.document = document;
        encoded = null;
        return this;
    }

    public void setEncoded(final String encoded) {
        parse(encoded);
    }

    public StateToken setFolder(final Long folder) {
        this.folder = folder == null ? null : folder.toString();
        encoded = null;
        return this;
    }

    public StateToken setFolder(final String folder) {
        this.folder = folder;
        encoded = null;
        return this;
    }

    public StateToken setGroup(final String group) {
        this.group = group;
        encoded = null;
        return this;
    }

    public StateToken setTool(final String tool) {
        this.tool = tool;
        encoded = null;
        return this;
    }

    @Override
    public String toString() {
        return getEncoded();
    }

    private String conditionalAssign(final int index, final String[] splitted) {
        if (splitted.length > index) {
            return splitted[index];
        } else {
            return null;
        }
    }

    private void parse(final String encoded) {
        String[] splitted;
        if (encoded != null && encoded.length() > 0) {
            splitted = encoded.split("\\.");
        } else {
            splitted = EMPTY;
        }
        setGroup(conditionalAssign(0, splitted));
        setTool(conditionalAssign(1, splitted));
        setFolder(conditionalAssign(2, splitted));
        setDocument(conditionalAssign(3, splitted));
    }
}
