/*
 * Copyright 2015 The Android Open Source Project
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
package android.media.cts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import junit.framework.Assert;

public class ResourceManagerStubActivity extends Activity {
    private static final String TAG = "ResourceManagerStubActivity";
    private final Object mFinishEvent = new Object();
    private int[] mRequestCodes = {0, 1};
    private boolean[] mResults = {false, false};
    private int mNumResults = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Activity " + requestCode + " finished.");
        mResults[requestCode] = (resultCode == RESULT_OK);
        if (++mNumResults == mResults.length) {
            synchronized (mFinishEvent) {
                mFinishEvent.notify();
            }
        }
    }

    public boolean testReclaimResource() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Context context = getApplicationContext();
                    Intent intent1 = new Intent(context, ResourceManagerTestActivity1.class);
                    startActivityForResult(intent1, mRequestCodes[0]);
                    Thread.sleep(5000);  // wait for process to launch and allocate all codecs.

                    Intent intent2 = new Intent(context, ResourceManagerTestActivity2.class);
                    startActivityForResult(intent2, mRequestCodes[1]);

                    synchronized (mFinishEvent) {
                        mFinishEvent.wait();
                    }
                } catch(Exception e) {
                    Log.d(TAG, "testReclaimResource got exception " + e.toString());
                }
            }
        };
        thread.start();
        thread.join(10000);

        for (int i = 0; i < mResults.length; ++i) {
            Assert.assertTrue("Result from activity " + i + " is a fail.", mResults[i]);
        }
        return true;
    }
}
