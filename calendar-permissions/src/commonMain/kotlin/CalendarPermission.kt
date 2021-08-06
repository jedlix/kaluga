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

package com.splendo.kaluga.permissions.calendar

import com.splendo.kaluga.permissions.BasePermissionsBuilder
import com.splendo.kaluga.permissions.PermissionContext
import com.splendo.kaluga.permissions.PermissionManager
import com.splendo.kaluga.permissions.PermissionStateRepo
import com.splendo.kaluga.permissions.defaultPermissionContext
import kotlin.coroutines.CoroutineContext

/**
 * A [PermissionManager] for managing [CalendarPermission]
 */
expect class CalendarPermissionManager : PermissionManager<CalendarPermission> {
    /**
     * The [CalendarPermission] managed by this manager
     */
    val calendar: CalendarPermission
}

interface BaseCalendarPermissionManagerBuilder: BasePermissionsBuilder {

    /**
     * Creates a [CalendarPermissionManager]
     * @param repo The [CalendarPermissionStateRepo] associated with the [CalendarPermission]
     */
    fun create(calendar: CalendarPermission, repo: CalendarPermissionStateRepo): PermissionManager<CalendarPermission>
}

/**
 * A builder for creating a [CalendarPermissionManager]
 */
expect class CalendarPermissionManagerBuilder(context: PermissionContext = defaultPermissionContext) : BaseCalendarPermissionManagerBuilder

/**
 * A [PermissionStateRepo] for [CalendarPermission]
 * @param builder The [CalendarPermissionManagerBuilder] for creating the [CalendarPermissionManager] associated with the permission
 * @param coroutineContext The [CoroutineContext] to run the state machine on.
 */
class CalendarPermissionStateRepo(calendar: CalendarPermission, builder: BaseCalendarPermissionManagerBuilder, coroutineContext: CoroutineContext) : PermissionStateRepo<CalendarPermission>(coroutineContext = coroutineContext) {

    override val permissionManager: PermissionManager<CalendarPermission> = builder.create(calendar, this)
}
