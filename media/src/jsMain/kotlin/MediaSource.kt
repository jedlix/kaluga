/*
 Copyright 2023 Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.media

/**
 * The source at which [PlayableMedia] can be found
 */
actual sealed class MediaSource {

    /**
     * A [MediaSource] for a url string
     * @property url the url string of the media source
     */
    data class URL(val url: String) : MediaSource()
}

/**
 * Attempts to create a [MediaSource] from a url string
 * @param url the url String of the media source
 * @return the [MediaSource] associated with [url] or `null` if none could be created
 */
actual fun mediaSourceFromUrl(url: String): MediaSource? = MediaSource.URL(url)
