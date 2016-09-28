# Copyright (C) 2011 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#
# Builds a package and defines a rule to generate the associated test
# package XML needed by CTS.
#
# Replace "include $(BUILD_PACKAGE)" with "include $(BUILD_CTS_PACKAGE)"
#

# Disable by default so "m cts" will work in emulator builds
LOCAL_DEX_PREOPT := false
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_STATIC_JAVA_LIBRARIES += platform-test-annotations

include $(BUILD_CTS_SUPPORT_PACKAGE)
include $(BUILD_CTS_MODULE_TEST_CONFIG)
