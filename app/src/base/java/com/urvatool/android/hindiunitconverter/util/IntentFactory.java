/*
 * Copyright 2016 Phil Shadlyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.urvatool.android.hindiunitconverter.util;


import android.content.Context;
import android.content.Intent;

/**
 * 'base' implementation of IntentFactory
 * Created by Phizz on 2016-11-13.
 */

public class IntentFactory extends BaseIntentFactory {

    /**
     * Base implementation of method, will not be used so returns null.
     *
     * @param context context
     * @return null
     */
    public static Intent getDonateIntent(final Context context) {
        return null;
    }
}
