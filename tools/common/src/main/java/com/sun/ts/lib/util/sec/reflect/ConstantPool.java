/*
 * Copyright (c) 2003, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.lib.util.sec.reflect;

import java.lang.reflect.*;

/**
 * Provides reflective access to the constant pools of classes. Currently this is needed to provide reflective access to
 * annotations but may be used by other internal subsystems in the future.
 */

public class ConstantPool {
    // Number of entries in this constant pool (= maximum valid constant pool
    // index)
    public int getSize() {
        return getSize0(constantPoolOop);
    }

    public Class getClassAt(int index) {
        return getClassAt0(constantPoolOop, index);
    }

    public Class getClassAtIfLoaded(int index) {
        return getClassAtIfLoaded0(constantPoolOop, index);
    }

    // Returns either a Method or Constructor.
    // Static initializers are returned as Method objects.
    public Member getMethodAt(int index) {
        return getMethodAt0(constantPoolOop, index);
    }

    public Member getMethodAtIfLoaded(int index) {
        return getMethodAtIfLoaded0(constantPoolOop, index);
    }

    public Field getFieldAt(int index) {
        return getFieldAt0(constantPoolOop, index);
    }

    public Field getFieldAtIfLoaded(int index) {
        return getFieldAtIfLoaded0(constantPoolOop, index);
    }

    // Fetches the class name, member (field, method or interface
    // method) name, and type descriptor as an array of three Strings
    public String[] getMemberRefInfoAt(int index) {
        return getMemberRefInfoAt0(constantPoolOop, index);
    }

    public int getIntAt(int index) {
        return getIntAt0(constantPoolOop, index);
    }

    public long getLongAt(int index) {
        return getLongAt0(constantPoolOop, index);
    }

    public float getFloatAt(int index) {
        return getFloatAt0(constantPoolOop, index);
    }

    public double getDoubleAt(int index) {
        return getDoubleAt0(constantPoolOop, index);
    }

    public String getStringAt(int index) {
        return getStringAt0(constantPoolOop, index);
    }

    public String getUTF8At(int index) {
        return getUTF8At0(constantPoolOop, index);
    }

    // ---------------------------------------------------------------------------
    // Internals only below this point
    //

    static {
        Reflection.registerFieldsToFilter(ConstantPool.class, new String[] { "constantPoolOop" });
    }

    // HotSpot-internal constant pool object (set by the VM, name known to the VM)
    private Object constantPoolOop;

    private native int getSize0(Object constantPoolOop);

    private native Class getClassAt0(Object constantPoolOop, int index);

    private native Class getClassAtIfLoaded0(Object constantPoolOop, int index);

    private native Member getMethodAt0(Object constantPoolOop, int index);

    private native Member getMethodAtIfLoaded0(Object constantPoolOop, int index);

    private native Field getFieldAt0(Object constantPoolOop, int index);

    private native Field getFieldAtIfLoaded0(Object constantPoolOop, int index);

    private native String[] getMemberRefInfoAt0(Object constantPoolOop, int index);

    private native int getIntAt0(Object constantPoolOop, int index);

    private native long getLongAt0(Object constantPoolOop, int index);

    private native float getFloatAt0(Object constantPoolOop, int index);

    private native double getDoubleAt0(Object constantPoolOop, int index);

    private native String getStringAt0(Object constantPoolOop, int index);

    private native String getUTF8At0(Object constantPoolOop, int index);
}
