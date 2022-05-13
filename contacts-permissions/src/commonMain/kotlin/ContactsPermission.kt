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

package com.splendo.kaluga.permissions.contacts

import com.splendo.kaluga.permissions.BasePermissionsBuilder
import com.splendo.kaluga.permissions.PermissionContext
import com.splendo.kaluga.permissions.PermissionManager
import com.splendo.kaluga.permissions.PermissionStateRepo
import com.splendo.kaluga.permissions.defaultPermissionContext
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A [PermissionManager] for managing [ContactsPermission]
 */
expect class ContactsPermissionManager : PermissionManager<ContactsPermission> {
    /**
     * The [Permission.Contacts] managed by this manager
     */
    val contactsPermission: ContactsPermission
}

interface BaseContactsPermissionManagerBuilder : BasePermissionsBuilder<ContactsPermission> {

    /**
     * Creates a [ContactsPermissionManager]
     * @param repo The [ContactsPermissionStateRepo] associated with the [ContactsPermission]
     */
    fun create(contactsPermission: ContactsPermission, repo: PermissionStateRepo<ContactsPermission>): PermissionManager<ContactsPermission>
}

/**
 * A builder for creating a [ContactsPermissionManager]
 */
expect class ContactsPermissionManagerBuilder(context: PermissionContext = defaultPermissionContext) : BaseContactsPermissionManagerBuilder

/**
 * A [PermissionStateRepo] for [ContactsPermission]
 * @param builder The [ContactsPermissionManagerBuilder] for creating the [ContactsPermissionManager] associated with the permission
 * @param coroutineContext The [CoroutineContext] to run the state machine on.
 */
class ContactsPermissionStateRepo(
    contactsPermission: ContactsPermission,
    builder: BaseContactsPermissionManagerBuilder,
    monitoringInterval: Long = defaultMonitoringInterval,
    coroutineContext: CoroutineContext = Dispatchers.Main.immediate
) : PermissionStateRepo<ContactsPermission>(monitoringInterval, coroutineContext, { builder.create(contactsPermission, it) })
