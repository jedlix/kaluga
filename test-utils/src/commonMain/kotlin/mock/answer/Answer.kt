/*
 Copyright 2022 Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.test.mock.answer

import com.splendo.kaluga.test.mock.parameters.ParametersSpec

sealed interface BaseAnswer<V : ParametersSpec.Values, R>
interface Answer<V : ParametersSpec.Values, R> : BaseAnswer<V, R> {
    fun call(arguments: V): R
}
interface SuspendedAnswer<V : ParametersSpec.Values, R> : BaseAnswer<V, R> {
    suspend fun call(arguments: V): R
}
