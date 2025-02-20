/*
 * Copyright (c) 1999, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.ts.lib.util.sec.misc;

import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.text.MessageFormat;
import java.lang.Character.*;

/**
 * This class holds all necessary information to install or upgrade a extension on the user's disk
 *
 * @author Jerome Dochez
 */
public class ExtensionInfo {

    /**
     *
     * public static values returned by the isCompatible method
     *
     */
    public static final int COMPATIBLE = 0;

    public static final int REQUIRE_SPECIFICATION_UPGRADE = 1;

    public static final int REQUIRE_IMPLEMENTATION_UPGRADE = 2;

    public static final int REQUIRE_VENDOR_SWITCH = 3;

    public static final int INCOMPATIBLE = 4;

    /**
     *
     * attributes fully describer an extension. The underlying described extension may be installed and requested.
     *
     */
    public String title;

    public String name;

    public String specVersion;

    public String specVendor;

    public String implementationVersion;

    public String vendor;

    public String vendorId;

    public String url;

    // For I18N support
    private static final ResourceBundle rb = ResourceBundle.getBundle("sun.misc.resources.Messages");

    /**
     *
     * Create a new uninitialized extension information object
     *
     */
    public ExtensionInfo() {
    }

    /**
     *
     * Create and initialize an extension information object. The initialization uses the attributes passed as being the
     * content of a manifest file to load the extension information from. Since manifest file may contain information on
     * several extension they may depend on, the extension key parameter is prepanded to the attribute name to make the key
     * used to retrieve the attribute from the manifest file
     *
     * 
     * @param extensionKey unique extension key in the manifest
     * @param attr Attributes of a manifest file
     */
    public ExtensionInfo(String extensionKey, Attributes attr) throws NullPointerException {
        String s;
        if (extensionKey != null) {
            s = extensionKey + "-";
        } else {
            s = "";
        }

        String attrKey = s + Name.EXTENSION_NAME.toString();
        name = attr.getValue(attrKey);
        if (name != null)
            name = name.trim();

        attrKey = s + Name.SPECIFICATION_TITLE.toString();
        title = attr.getValue(attrKey);
        if (title != null)
            title = title.trim();

        attrKey = s + Name.SPECIFICATION_VERSION.toString();
        specVersion = attr.getValue(attrKey);
        if (specVersion != null)
            specVersion = specVersion.trim();

        attrKey = s + Name.SPECIFICATION_VENDOR.toString();
        specVendor = attr.getValue(attrKey);
        if (specVendor != null)
            specVendor = specVendor.trim();

        attrKey = s + Name.IMPLEMENTATION_VERSION.toString();
        implementationVersion = attr.getValue(attrKey);
        if (implementationVersion != null)
            implementationVersion = implementationVersion.trim();

        attrKey = s + Name.IMPLEMENTATION_VENDOR.toString();
        vendor = attr.getValue(attrKey);
        if (vendor != null)
            vendor = vendor.trim();

        attrKey = s + Name.IMPLEMENTATION_VENDOR_ID.toString();
        vendorId = attr.getValue(attrKey);
        if (vendorId != null)
            vendorId = vendorId.trim();

        attrKey = s + Name.IMPLEMENTATION_URL.toString();
        url = attr.getValue(attrKey);
        if (url != null)
            url = url.trim();
    }

    /**
     *
     * 
     * @return true if the extension described by this extension information is compatible with the extension described by
     * the extension information passed as a parameter
     *
     *
     * @param the requested extension information to compare to
     */
    public int isCompatibleWith(ExtensionInfo ei) {

        if (name == null || ei.name == null)
            return INCOMPATIBLE;
        if (name.compareTo(ei.name) == 0) {
            // is this true, if not spec version is specified, we consider
            // the value as being "any".
            if (specVersion == null || ei.specVersion == null)
                return COMPATIBLE;

            int version = compareExtensionVersion(specVersion, ei.specVersion);
            if (version < 0) {
                // this extension specification is "older"
                if (vendorId != null && ei.vendorId != null) {
                    if (vendorId.compareTo(ei.vendorId) != 0) {
                        return REQUIRE_VENDOR_SWITCH;
                    }
                }
                return REQUIRE_SPECIFICATION_UPGRADE;
            } else {
                // the extension spec is compatible, let's look at the
                // implementation attributes
                if (vendorId != null && ei.vendorId != null) {
                    // They care who provides the extension
                    if (vendorId.compareTo(ei.vendorId) != 0) {
                        // They want to use another vendor implementation
                        return REQUIRE_VENDOR_SWITCH;
                    } else {
                        // Vendor matches, let's see the implementation version
                        if (implementationVersion != null && ei.implementationVersion != null) {
                            // they care about the implementation version
                            version = compareExtensionVersion(implementationVersion, ei.implementationVersion);
                            if (version < 0) {
                                // This extension is an older implementation
                                return REQUIRE_IMPLEMENTATION_UPGRADE;
                            }
                        }
                    }
                }
                // All othe cases, we consider the extensions to be compatible
                return COMPATIBLE;
            }
        }
        return INCOMPATIBLE;
    }

    /**
     *
     * helper method to print sensible information on the undelying described extension
     *
     */
    public String toString() {
        return "Extension : title(" + title + "), name(" + name + "), spec vendor(" + specVendor + "), spec version(" + specVersion
                + "), impl vendor(" + vendor + "), impl vendor id(" + vendorId + "), impl version(" + implementationVersion + "), impl url("
                + url + ")";
    }

    /*
     * helper method to compare two versions. version are in the x.y.z.t pattern. </p>
     * 
     * @param source version to compare to
     * 
     * @param target version used to compare against
     * 
     * @return < 0 if source < version > 0 if source > version = 0 if source = version
     */
    private int compareExtensionVersion(String source, String target) throws NumberFormatException {
        source = source.toLowerCase();
        target = target.toLowerCase();

        return strictCompareExtensionVersion(source, target);
    }

    /*
     * helper method to compare two versions. version are in the x.y.z.t pattern. </p>
     * 
     * @param source version to compare to
     * 
     * @param target version used to compare against
     * 
     * @return < 0 if source < version > 0 if source > version = 0 if source = version
     */
    private int strictCompareExtensionVersion(String source, String target) throws NumberFormatException {
        if (source.equals(target))
            return 0;

        StringTokenizer stk = new StringTokenizer(source, ".,");
        StringTokenizer ttk = new StringTokenizer(target, ".,");

        // Compare number
        int n = 0, m = 0, result = 0;

        // Convert token into meaning number for comparision
        if (stk.hasMoreTokens())
            n = convertToken(stk.nextToken().toString());

        // Convert token into meaning number for comparision
        if (ttk.hasMoreTokens())
            m = convertToken(ttk.nextToken().toString());

        if (n > m)
            return 1;
        else if (m > n)
            return -1;
        else {
            // Look for index of "." in the string
            int sIdx = source.indexOf(".");
            int tIdx = target.indexOf(".");

            if (sIdx == -1)
                sIdx = source.length() - 1;

            if (tIdx == -1)
                tIdx = target.length() - 1;

            return strictCompareExtensionVersion(source.substring(sIdx + 1), target.substring(tIdx + 1));
        }
    }

    private int convertToken(String token) {
        if (token == null || token.equals(""))
            return 0;

        int charValue = 0;
        int charVersion = 0;
        int patchVersion = 0;
        int strLength = token.length();
        int endIndex = strLength;
        char lastChar;

        Object[] args = { name };
        MessageFormat mf = new MessageFormat(rb.getString("optpkg.versionerror"));
        String versionError = mf.format(args);

        // Look for "-" for pre-release
        int prIndex = token.indexOf("-");

        // Look for "_" for patch release
        int patchIndex = token.indexOf("_");

        if (prIndex == -1 && patchIndex == -1) {
            // This is a FCS release
            try {
                return Integer.parseInt(token) * 100;
            } catch (NumberFormatException e) {
                System.out.println(versionError);
                return 0;
            }
        } else if (patchIndex != -1) {
            // This is a patch (update) release
            int prversion;
            try {
                // Obtain the version
                prversion = Integer.parseInt(token.substring(0, patchIndex));

                // Check to see if the patch version is in the n.n.n_nnl format (special
                // release)
                lastChar = token.charAt(strLength - 1);
                if (Character.isLetter(lastChar)) {
                    // letters a-z have values from 10-35
                    charValue = Character.getNumericValue(lastChar);
                    endIndex = strLength - 1;

                    // Obtain the patch version id
                    patchVersion = Integer.parseInt(token.substring(patchIndex + 1, endIndex));

                    if (charValue >= Character.getNumericValue('a') && charValue <= Character.getNumericValue('z')) {
                        // This is a special release
                        charVersion = (patchVersion * 100) + charValue;
                    } else {
                        // character is not a a-z letter, ignore
                        charVersion = 0;
                        System.out.println(versionError);
                    }
                } else {
                    // This is a regular update release. Obtain the patch version id
                    patchVersion = Integer.parseInt(token.substring(patchIndex + 1, endIndex));
                }
            } catch (NumberFormatException e) {
                System.out.println(versionError);
                return 0;
            }
            return prversion * 100 + (patchVersion + charVersion);
        } else {
            // This is a milestone release, either a early access, alpha, beta, or RC

            // Obtain the version
            int mrversion;
            try {
                mrversion = Integer.parseInt(token.substring(0, prIndex));
            } catch (NumberFormatException e) {
                System.out.println(versionError);
                return 0;
            }

            // Obtain the patch version string, including the milestone + version
            String prString = token.substring(prIndex + 1);

            // Milestone version
            String msVersion = "";
            int delta = 0;

            if (prString.indexOf("ea") != -1) {
                msVersion = prString.substring(2);
                delta = 50;
            } else if (prString.indexOf("alpha") != -1) {
                msVersion = prString.substring(5);
                delta = 40;
            } else if (prString.indexOf("beta") != -1) {
                msVersion = prString.substring(4);
                delta = 30;
            } else if (prString.indexOf("rc") != -1) {
                msVersion = prString.substring(2);
                delta = 20;
            }

            if (msVersion == null || msVersion.equals("")) {
                // No version after the milestone, assume 0
                return mrversion * 100 - delta;
            } else {
                // Convert the milestone version
                try {
                    return mrversion * 100 - delta + Integer.parseInt(msVersion);
                } catch (NumberFormatException e) {
                    System.out.println(versionError);
                    return 0;
                }
            }
        }
    }
}
