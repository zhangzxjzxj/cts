/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.android.compatibility.common.tradefed.targetprep;

import com.android.tradefed.build.IBuildInfo;
import com.android.tradefed.config.Option;
import com.android.tradefed.config.OptionClass;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.device.ITestDevice;
import com.android.tradefed.targetprep.BuildError;
import com.android.tradefed.targetprep.TargetSetupError;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks that a given setting on the device is one of the given values
 */
@OptionClass(alias="settings-preparer")
public class SettingsPreparer extends PreconditionPreparer {

    public enum SettingType {
        SECURE,
        GLOBAL,
        SYSTEM;
    }

    @Option(name = "device-setting", description = "The setting on the device to be checked",
            mandatory = true)
    protected String mSettingName = null;

    @Option(name = "setting-type",
            description = "If the setting is 'secure', 'global', or 'system'", mandatory = true)
    protected SettingType mSettingType = null;

    @Option(name = "set-value", description = "The value to be set for the setting")
    protected String mSetValue = null;

    @Option(name = "expected-values", description = "The set of expected values of the setting")
    protected List<String> mExpectedSettingValues = new ArrayList<String>();

    @Override
    public void run(ITestDevice device, IBuildInfo buildInfo) throws TargetSetupError,
            BuildError, DeviceNotAvailableException {

        /* At least one of the options "set-value" and "expected-values" must be set */
        if (mSetValue == null && mExpectedSettingValues.isEmpty()) {
            throw new TargetSetupError("At least one of the options \"set-value\" and " +
                    "\"expected-values\" must be set");
        }

        String shellCmdGet = (!mExpectedSettingValues.isEmpty()) ?
                String.format("settings get %s %s", mSettingType, mSettingName) : "";
        String shellCmdPut = (mSetValue != null) ?
                String.format("settings put %s %s %s", mSettingType, mSettingName, mSetValue) : "";


        /* Case 1: Both expected-values and set-value are given */
        if (mSetValue != null && !mExpectedSettingValues.isEmpty()) {
            // first ensure that the set-value given can be found in expected-values
            if (!mExpectedSettingValues.contains(mSetValue)) {
                throw new TargetSetupError(String.format(
                        "set-value for %s is %s, but value not found in expected-values: %s",
                        mSettingName, mSetValue, mExpectedSettingValues.toString()));
            }
            String currentSettingValue = device.executeShellCommand(shellCmdGet).trim();
            // only change unexpected setting value
            if (!mExpectedSettingValues.contains(currentSettingValue)) {
                device.executeShellCommand(shellCmdPut);
            }
            return;
        }

        /* Case 2: Only set-value given */
        if (mSetValue != null) {
            device.executeShellCommand(shellCmdPut);
            return;
        }

        /* Case 3: Only expected-values given */
        String currentSettingValue = device.executeShellCommand(shellCmdGet).trim();
        if (!mExpectedSettingValues.contains(currentSettingValue)) {
            throw new TargetSetupError(
                    String.format("Device setting \"%s\" returned \"%s\", not found in %s",
                    mSettingName, currentSettingValue, mExpectedSettingValues.toString()));
        }
    }

}
