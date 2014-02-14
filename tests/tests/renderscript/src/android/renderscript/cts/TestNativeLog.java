/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Don't edit this file!  It is auto-generated by frameworks/rs/api/gen_runtime.

package android.renderscript.cts;

import android.renderscript.Allocation;
import android.renderscript.RSRuntimeException;
import android.renderscript.Element;

public class TestNativeLog extends RSBaseCompute {

    private ScriptC_TestNativeLog script;
    private ScriptC_TestNativeLogRelaxed scriptRelaxed;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        script = new ScriptC_TestNativeLog(mRS);
        scriptRelaxed = new ScriptC_TestNativeLogRelaxed(mRS);
    }

    private void checkNativeLogFloatFloat() {
        Allocation inV = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 1, 0xe1bdef5f88d6ab65L);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            script.forEach_testNativeLogFloatFloat(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloatFloat: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 1), INPUTSIZE);
            scriptRelaxed.forEach_testNativeLogFloatFloat(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloatFloat: " + e.toString());
        }
    }

    private void checkNativeLogFloat2Float2() {
        Allocation inV = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 2, 0x97f97cf88a22a571L);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            script.forEach_testNativeLogFloat2Float2(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat2Float2: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 2), INPUTSIZE);
            scriptRelaxed.forEach_testNativeLogFloat2Float2(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat2Float2: " + e.toString());
        }
    }

    private void checkNativeLogFloat3Float3() {
        Allocation inV = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 3, 0xc4eacc58da38c0cbL);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            script.forEach_testNativeLogFloat3Float3(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat3Float3: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 3), INPUTSIZE);
            scriptRelaxed.forEach_testNativeLogFloat3Float3(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat3Float3: " + e.toString());
        }
    }

    private void checkNativeLogFloat4Float4() {
        Allocation inV = CreateRandomAllocation(mRS, Element.DataType.FLOAT_32, 4, 0xf1dc1bb92a4edc25L);
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            script.forEach_testNativeLogFloat4Float4(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat4Float4: " + e.toString());
        }
        try {
            Allocation out = Allocation.createSized(mRS, GetElement(mRS, Element.DataType.FLOAT_32, 4), INPUTSIZE);
            scriptRelaxed.forEach_testNativeLogFloat4Float4(inV, out);
        } catch (Exception e) {
            throw new RSRuntimeException("RenderScript. Can't invoke forEach_testNativeLogFloat4Float4: " + e.toString());
        }
    }

    public void testNativeLog() {
        checkNativeLogFloatFloat();
        checkNativeLogFloat2Float2();
        checkNativeLogFloat3Float3();
        checkNativeLogFloat4Float4();
    }
}
