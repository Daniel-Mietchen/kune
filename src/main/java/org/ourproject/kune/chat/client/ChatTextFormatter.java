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

package org.ourproject.kune.chat.client;

import java.util.HashMap;

import org.ourproject.kune.chat.client.rooms.ui.RoomImages;

import com.google.gwt.user.client.ui.HTML;

public class ChatTextFormatter {

    private static final String JOYFUL = "KuneProtIniJOYFULKuneProtEnd";
    private static final String ANGRY = "KuneProtIniANGRYKuneProtEnd";
    private static final String BLUSHING = "KuneProtIniBLUSHINGKuneProtEnd";
    private static final String CRYING = "KuneProtIniCRYINGKuneProtEnd";
    private static final String POUTY = "KuneProtIniPOUTYKuneProtEnd";
    private static final String SURPRISED = "KuneProtIniSURPRISEDKuneProtEnd";
    private static final String GRIN = "KuneProtIniGRINKuneProtEnd";
    private static final String ANGEL = "KuneProtIniANGELKuneProtEnd";
    private static final String KISSING = "KuneProtIniKISSINGKuneProtEnd";
    private static final String SMILE = "KuneProtIniSMILEKuneProtEnd";
    private static final String TONGUE = "KuneProtIniTONGUEKuneProtEnd";
    private static final String UNCERTAIN = "KuneProtIniUNCERTAINKuneProtEnd";
    private static final String COOL = "KuneProtIniCOOLKuneProtEnd";
    private static final String WINK = "KuneProtIniWINKKuneProtEnd";
    private static final String HAPPY = "KuneProtIniHAPPYKuneProtEnd";
    private static final String ALIEN = "KuneProtIniALIENKuneProtEnd";
    private static final String ANDY = "KuneProtIniANDYKuneProtEnd";
    private static final String DEVIL = "KuneProtIniDEVILKuneProtEnd";
    private static final String LOL = "KuneProtIniLOLKuneProtEnd";
    private static final String NINJA = "KuneProtIniNINJAKuneProtEnd";
    private static final String SAD = "KuneProtIniSADKuneProtEnd";
    private static final String SICK = "KuneProtIniSICKKuneProtEnd";
    private static final String SIDEWAYS = "KuneProtIniSIDEWAYSKuneProtEnd";
    private static final String SLEEPING = "KuneProtIniSLEEPINGKuneProtEnd";
    private static final String UNSURE = "KuneProtIniUNSUREKuneProtEnd";
    private static final String WONDERING = "KuneProtIniWONDERINGKuneProtEnd";
    private static final String LOVE = "KuneProtIniLOVEKuneProtEnd";
    private static final String PINCHED = "KuneProtIniPINCHEDKuneProtEnd";
    private static final String POLICEMAN = "KuneProtIniPOLICEMANKuneProtEnd";
    private static final String W00T = "KuneProtIniWOOTKuneProtEnd";
    private static final String WHISTLING = "KuneProtIniWHISLINGKuneProtEnd";
    private static final String WIZARD = "KuneProtIniWIZARDKuneProtEnd";
    private static final String BANDIT = "KuneProtIniBANDITKuneProtEnd";
    private static final String HEART = "KuneProtIniHEARTKuneProtectRepEnd";

    private final HashMap replacements;
    private final String[] replacementList;

    public ChatTextFormatter() {
	replacements = new HashMap();
	replacements.put(ALIEN, new String[] { "=:)", "(alien)" });
	replacements
		.put(ANDY, new String[] { "o_O", "o_0", "O_O", "o_o", "O_o", "0_o", "o0", "0o", "oO", "Oo", "0_0" });
	replacements.put(ANGEL, new String[] { "O:)", "o:)", "o:-)", "O:-)", "0:)", "0:-)" });
	replacements.put(ANGRY, new String[] { "&gt;:o", "&gt;:-o", "&gt;:O", "&gt;:-O", "X(", "X-(", "x(", "x-(",
		":@", "&lt;_&lt;" });
	replacements.put(BANDIT, new String[] { "(bandit)", ":(&gt;" });
	replacements.put(BLUSHING, new String[] { ":\"&gt;", ":*&gt;", ":-$", ":$" });
	replacements.put(COOL, new String[] { "B)", "B-)", "8)" });
	replacements.put(CRYING, new String[] { ":'(", "='(" });
	replacements.put(DEVIL, new String[] { "&gt;:)" });
	replacements.put(GRIN, new String[] { ":-d", ":d", ":-D", ":D", ":d", "=D", "=-D" });
	replacements.put(HAPPY, new String[] { "=)", "=-)" });
	replacements.put(HEART, new String[] { "(L)", "(h)", "(H)" });
	replacements.put(JOYFUL, new String[] { "^_^", "^-^", "^^", ":))", ":-))" });
	replacements.put(KISSING, new String[] { ":-*", ":*" });
	replacements.put(LOL, new String[] { "(LOL)", "lol" });
	replacements.put(LOVE, new String[] { ":-X", ":-xX", ":x", "(wubya)", "(wubyou)", "(wub)" });
	replacements.put(NINJA, new String[] { "(:)", "(ph33r)", "(ph34r)" });
	replacements.put(PINCHED, new String[] { "&gt;_&lt;" });
	replacements.put(POLICEMAN, new String[] { "(police)", "(cop)", "{):)" });
	replacements.put(POUTY, new String[] { ":|", "=|", ":-|" });
	replacements.put(SAD, new String[] { ":(", "=(", "=-(", ":-(" });
	replacements.put(SICK, new String[] { ":&amp;", ":-&amp;" });
	replacements.put(SIDEWAYS, new String[] { "=]" });
	replacements.put(SLEEPING, new String[] { "(-.-)", "|)", "|-)", "I-)", "I-|" });
	replacements.put(SMILE, new String[] { ":-)", ":)" });
	replacements.put(SURPRISED, new String[] { ":-O", ":O", ":-o", ":o", ":-0", "=-O", "=-o", "=o", "=O" });
	replacements.put(TONGUE, new String[] { ":P", "=P", "=p", ":-P", ":p", ":-p", ":b" });
	replacements.put(UNCERTAIN, new String[] { ":-\\", ":-/", ":/", ":\\" });
	replacements.put(UNSURE, new String[] { ":s", ":-S", ":-s", ":S" });
	replacements.put(W00T, new String[] { "(woot)", "(w00t)", "(wOOt)" });
	replacements.put(WHISTLING, new String[] { ":-\"" });
	replacements.put(WINK, new String[] { ";)", ";-)", ";&gt;" });
	replacements.put(WIZARD, new String[] { "(wizard)" });
	replacements.put(WONDERING, new String[] { ":?" });

	replacementList = new String[] { JOYFUL, ANGRY, BLUSHING, CRYING, POUTY, SURPRISED, GRIN, ANGEL, KISSING,
		SMILE, TONGUE, UNCERTAIN, COOL, WINK, HAPPY, ALIEN, ANDY, DEVIL, LOL, NINJA, SAD, SICK, SIDEWAYS,
		SLEEPING, UNSURE, WONDERING, LOVE, PINCHED, POLICEMAN, W00T, WHISTLING, WIZARD, BANDIT, HEART };
    }

    public static HTML format(String message) {
	message = message.replaceAll("&", "&amp;");
	message = message.replaceAll("\"", "&quot;");
	message = message.replaceAll("<", "&lt;");
	message = message.replaceAll(">", "&gt;");
	message = message.replaceAll("\n", "<br>\n");

	message = formatEmoticons(message);

	return new HTML(message);
    }

    //
    // private String formatEmoticonsNew(final String initMessage) {
    // String message = initMessage;
    //
    //
    // for (int i = 0; i < replacementEnums.length; i++) {
    // String[] replacement = (String[]) replacements.get(replacementEnums[i]);
    // // TODO
    // }
    //
    // return null;
    // }

    private static String formatEmoticons(final String initMessage) {
	String message = initMessage;
	message = message.replaceAll(":\\)", SMILE);
	message = message.replaceAll(":-\\)", SMILE);

	message = message.replaceAll("X-\\(", ANGRY);
	message = message.replaceAll("X\\(", ANGRY);

	message = message.replaceAll(":-D", GRIN);
	message = message.replaceAll(":D", GRIN);

	message = message.replaceAll(":\\(", SAD);
	message = message.replaceAll(":-\\(", SAD);

	message = message.replaceAll(":P", TONGUE);

	message = message.replaceAll(":\'\\(", CRYING);
	message = message.replaceAll(":\'\\(", CRYING);

	message = message.replaceAll(":-O", SURPRISED);
	message = message.replaceAll(":O", SURPRISED);

	message = message.replaceAll(":-\\*", KISSING);
	message = message.replaceAll(":\\*", KISSING);

	message = message.replaceAll(":-/", UNCERTAIN);
	message = message.replaceAll(":-/", UNCERTAIN);

	message = message.replaceAll(";\\)", WINK);
	message = message.replaceAll(";-\\)", WINK);

	message = message.replaceAll(":\\?", WONDERING);
	message = message.replaceAll(":\\?", WONDERING);

	message = message.replaceAll(":-X", LOVE);
	message = message.replaceAll(":-xX", LOVE);

	message = message.replaceAll(SMILE, RoomImages.App.getInstance().smile().getHTML());
	message = message.replaceAll(ANGRY, RoomImages.App.getInstance().angry().getHTML());
	message = message.replaceAll(GRIN, RoomImages.App.getInstance().grin().getHTML());
	message = message.replaceAll(SAD, RoomImages.App.getInstance().sad().getHTML());
	message = message.replaceAll(TONGUE, RoomImages.App.getInstance().tongue().getHTML());
	message = message.replaceAll(CRYING, RoomImages.App.getInstance().crying().getHTML());
	message = message.replaceAll(SURPRISED, RoomImages.App.getInstance().surprised().getHTML());
	message = message.replaceAll(KISSING, RoomImages.App.getInstance().kissing().getHTML());
	message = message.replaceAll(UNCERTAIN, RoomImages.App.getInstance().uncertain().getHTML());
	message = message.replaceAll(WINK, RoomImages.App.getInstance().wink().getHTML());
	message = message.replaceAll(WONDERING, RoomImages.App.getInstance().wondering().getHTML());
	message = message.replaceAll(LOVE, RoomImages.App.getInstance().love().getHTML());

	return message;
    }
}
