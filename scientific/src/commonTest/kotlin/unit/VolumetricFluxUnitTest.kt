/*
 Copyright 2021 Splendo Consulting B.V. The Netherlands

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

package com.splendo.kaluga.scientific.unit

import com.splendo.kaluga.scientific.PhysicalQuantity
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.converter.voltage.times
import com.splendo.kaluga.scientific.converter.volumetricFlow.times
import com.splendo.kaluga.scientific.converter.volumetricFlow.volumetricFlow
import com.splendo.kaluga.scientific.converter.volumetricFlux.times
import com.splendo.kaluga.scientific.invoke
import kotlin.jvm.JvmName
import kotlin.test.Test
import kotlin.test.assertEquals

class VolumetricFluxUnitTest {

    @Test
    fun metricToImperialConversionTest() {
        assertEquals(39.37, (CubicMeter per Second per SquareMeter).convert(1.0, CubicInch per Second per SquareInch, 2))
        assertEquals(22.71, (CubicMeter per Second per SquareMeter).convert(1.0, ImperialFluidOunce per Second per SquareInch, 2))
        assertEquals(0.0000063, (CubicMeter per Second per SquareMeter).convert(1.0, AcreInch per Second per SquareInch, 7))
    }

    @Test
    fun volumetricFluxToXConversionTest() {
        // convertToVolumetricFlow
        assertEquals(4(CubicMeter per Second), 2(CubicMeter per Second per SquareMeter) * 2(SquareMeter))
        assertEquals(4(CubicInch per Second), 2(CubicInch per Second per SquareInch) * 2(SquareInch))
        assertEquals(4(ImperialFluidOunce per Second), 2(ImperialFluidOunce per Second per SquareInch) * 2(SquareInch))
        assertEquals(4(AcreInch per Second), 2(AcreInch per Second per SquareInch) * 2(SquareInch))

        val calculated = 2(CubicMeter per Second per SquareMeter) * 2(SquareInch)
        assertEquals(2(CubicMeter per Second), calculated)
    }
}