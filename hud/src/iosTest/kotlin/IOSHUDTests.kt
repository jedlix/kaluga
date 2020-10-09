/*

Copyright 2019 Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.hud

import co.touchlab.stately.concurrency.AtomicReference
import com.splendo.kaluga.base.utils.EmptyCompletableDeferred
import com.splendo.kaluga.base.utils.complete
import com.splendo.kaluga.logging.debug
import kotlinx.coroutines.Dispatchers
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import kotlin.native.concurrent.ensureNeverFrozen

class IOSHUDTests : HUDTests() {

    class HUDViewController : UIViewController(null, null) {

        init {
            this.ensureNeverFrozen()
        }

        var mockPresentingHUD: MockPresentingHUD? = null

        override fun presentedViewController(): UIViewController? {
            return mockPresentingHUD
        }

        override fun presentViewController(viewControllerToPresent: UIViewController, animated: Boolean, completion: (() -> Unit)?) {
            (viewControllerToPresent as? MockPresentingHUD)?.let {
                mockPresentingHUD = it
                it.parent = this
                completion?.invoke()
            }
        }

        override fun dismissViewControllerAnimated(flag: Boolean, completion: (() -> Unit)?) {
            mockPresentingHUD?.parent = null
            mockPresentingHUD = null
            completion?.invoke()
        }
    }

    class MockPresentingHUD(val hudViewController: UIViewController) : UIViewController(null, null) {

        init {
            this.ensureNeverFrozen()
        }

        var parent: UIViewController? = null

        override fun presentingViewController(): UIViewController? {
            return parent
        }
    }

    override val builder = createBuilder(HUDViewController())
    private fun createBuilder(hostView: UIViewController): HUD.Builder = HUD.Builder(hostView, { MockPresentingHUD(it) })

    @Test
    fun presentIndicator() = runBlocking {
        val hostView = HUDViewController()
        val indicator = createBuilder(hostView).build()
        assertNull(hostView.presentedViewController)
        assertFalse(indicator.isVisible)
        indicator.present(false)
        assertTrue(indicator.isVisible)
        hostView.mockPresentingHUD?.parent = null
        hostView.mockPresentingHUD = null
    }

    @Test
    fun dismissIndicator() = runBlocking {
        val hostView = HUDViewController()
        val indicator = createBuilder(hostView).build()
        assertNull(hostView.presentedViewController)
        assertFalse(indicator.isVisible)
        indicator.present(false)
        assertTrue(indicator.isVisible)
        indicator.dismiss(false)
        assertFalse(indicator.isVisible)
        hostView.mockPresentingHUD?.parent = null
        hostView.mockPresentingHUD = null
    }
}
