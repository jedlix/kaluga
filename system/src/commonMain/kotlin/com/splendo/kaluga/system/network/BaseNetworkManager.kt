/*
 Copyright 2020 Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.system.network

typealias NetworkStateChange =  (Network) -> Unit

abstract class BaseNetworkManager(private val onNetworkStateChange: NetworkStateChange) {

    internal abstract suspend fun startMonitoringNetwork()
    internal abstract suspend fun stopMonitoringNetwork()

    internal fun handleNetworkStateChanged(network: Network) {
        onNetworkStateChange(network)
    }
}

expect class NetworkManager(context: Any? = null, onNetworkStateChange: NetworkStateChange) : BaseNetworkManager
