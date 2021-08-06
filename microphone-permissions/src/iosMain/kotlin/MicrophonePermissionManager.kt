/*
 Copyright (c) 2020. Splendo Consulting B.V. The Netherlands

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */

package com.splendo.kaluga.permissions.microphone

import com.splendo.kaluga.permissions.PermissionContext
import com.splendo.kaluga.permissions.av.AVPermissionHelper
import com.splendo.kaluga.permissions.PermissionManager
import com.splendo.kaluga.permissions.PermissionState
import com.splendo.kaluga.permissions.microphone.av.AVTypeMicrophone
import platform.Foundation.NSBundle

actual class MicrophonePermissionManager(
    private val bundle: NSBundle,
    stateRepo: MicrophonePermissionStateRepo
) : PermissionManager<MicrophonePermission>(stateRepo) {

    private val avPermissionHelper = AVPermissionHelper(bundle, AVTypeMicrophone(this))

    override suspend fun requestPermission() {
        avPermissionHelper.requestPermission()
    }

    override suspend fun initializeState(): PermissionState<MicrophonePermission> {
        return avPermissionHelper.initializeState()
    }

    override suspend fun startMonitoring(interval: Long) {
        avPermissionHelper.startMonitoring(interval)
    }

    override suspend fun stopMonitoring() {
        avPermissionHelper.stopMonitoring()
    }
}

actual class MicrophonePermissionManagerBuilder actual constructor(private val context: PermissionContext) : BaseMicrophonePermissionManagerBuilder {

    override fun create(repo: MicrophonePermissionStateRepo): PermissionManager<MicrophonePermission> {
        return MicrophonePermissionManager(context, repo)
    }
}
