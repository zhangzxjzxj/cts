/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.android.cts.verifier.bluetooth;

import com.android.cts.verifier.PassFailButtons;
import com.android.cts.verifier.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class BleScannerPrivacyMacActivity extends PassFailButtons.Activity {

    private static final String TAG = "BleScannerPrivacyMac";

    private int mMacCount;
    private TextView mMacText;
    private TextView mCountText;
    private TextView mTimerText;
    private CountDownTimer mTimer;
    private static final long REFRESH_MAC_TIME = 930000; // 15.5 min

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_scanner_privacy_mac);
        setPassFailButtonClickListeners();
        setInfoResources(R.string.ble_privacy_mac_name,
                         R.string.ble_privacy_mac_info, -1);
        getPassButton().setEnabled(false);
        mMacCount = 0;
        mMacText = (TextView)findViewById(R.id.ble_mac_address);
        mCountText = (TextView)findViewById(R.id.ble_mac_count);
        mTimerText = (TextView)findViewById(R.id.ble_timer);

        mTimer = new CountDownTimer(REFRESH_MAC_TIME, 1000) {
            @Override
            public void onTick(long millis) {
                int min = (int)millis / 60000;
                int sec = ((int)millis / 1000) % 60;
                mTimerText.setText(min + ":" + sec);
            }

            @Override
            public void onFinish() {
                mTimerText.setTextColor(getResources().getColor(R.color.red));
                mTimerText.setText("Time is up!");
                stop();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BleScannerService.BLE_PRIVACY_NEW_MAC_RECEIVE);
        filter.addAction(BleScannerService.BLE_MAC_ADDRESS);
        registerReceiver(onBroadcast, filter);

        if (mMacCount == 0) {
            startService(new Intent(this, BleScannerService.class));
        }
    }


    private BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BleScannerService.BLE_PRIVACY_NEW_MAC_RECEIVE:
                    mMacText.append(", " + intent.getStringExtra(BleScannerService.EXTRA_MAC_ADDRESS));
                    Toast.makeText(context, "New MAC address detected", Toast.LENGTH_SHORT).show();
                    mTimerText.setTextColor(getResources().getColor(R.color.green));
                    mTimerText.append("   Get new MAC address.");
                    mTimer.cancel();
                    getPassButton().setEnabled(true);
                    break;
                case BleScannerService.BLE_MAC_ADDRESS:
                    if (mMacCount == 0) {
                        mMacText.setText(intent.getStringExtra(BleScannerService.EXTRA_MAC_ADDRESS));
                        mTimer.start();
                    }
                    mMacCount++;
                    mCountText.setText("Count: " + mMacCount);
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(onBroadcast);
    }

    private void stop() {
        stopService(new Intent(this, BleScannerService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }
}
