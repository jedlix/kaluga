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

package com.splendo.kaluga.base.utils

actual typealias KalugaDateHolder = kotlin.js.Date

// TODO Implement with proper date solution for Java Script
actual class DefaultKalugaDate internal constructor(override val date: KalugaDateHolder) : KalugaDate {

    actual companion object {
        actual fun now(offsetInMilliseconds: Long, timeZone: TimeZone, locale: Locale): KalugaDate = DefaultKalugaDate(kotlin.js.Date(kotlin.js.Date.now() + offsetInMilliseconds))
        actual fun epoch(offsetInMilliseconds: Long, timeZone: TimeZone, locale: Locale): KalugaDate = DefaultKalugaDate(kotlin.js.Date(offsetInMilliseconds))
    }

    override var timeZone: TimeZone
        get() = TimeZone()
        set(_) { }

    override var era: Int
        get() = 0
        set(_) { }
    override var year: Int
        get() = date.getFullYear()
        set(_) { }
    override var month: Int
        get() = date.getMonth()
        set(_) { }
    override val daysInMonth: Int = 0
    override var weekOfYear: Int
        get() = 0
        set(_) { }
    override var weekOfMonth: Int
        get() = 0
        set(_) { }
    override var day: Int
        get() = 0
        set(_) { }
    override var dayOfYear: Int
        get() = date.getDay()
        set(_) { }
    override var weekDay: Int
        get() = date.getDate() + 1
        set(_) { }
    override var firstWeekDay: Int
        get() = 1
        set(_) { }

    override var hour: Int
        get() = date.getHours()
        set(_) { }
    override var minute: Int
        get() = date.getMinutes()
        set(_) { }
    override var second: Int
        get() = date.getSeconds()
        set(_) { }
    override var millisecond: Int
        get() = date.getMilliseconds()
        set(_) { }
    override var millisecondSinceEpoch: Long
        get() = date.getTime().toLong()
        set(_) { }

    override fun copy(): KalugaDate = DefaultKalugaDate(kotlin.js.Date(date.getMilliseconds()))

    override fun equals(other: Any?): Boolean {
        return (other as? KalugaDate)?.let {
            timeZone == other.timeZone && millisecondSinceEpoch == other.millisecondSinceEpoch
        } ?: false
    }

    override fun compareTo(other: KalugaDate): Int {
        return when {
            date.getMilliseconds() < other.millisecond -> -1
            date.getMilliseconds() == other.millisecond -> 0
            else -> 1
        }
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }
}
