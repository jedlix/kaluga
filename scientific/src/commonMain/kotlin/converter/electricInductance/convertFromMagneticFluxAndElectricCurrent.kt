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

package com.splendo.kaluga.scientific.converter.electricInductance

import com.splendo.kaluga.base.utils.Decimal
import com.splendo.kaluga.scientific.DefaultScientificValue
import com.splendo.kaluga.scientific.MeasurementType
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.byDividing
import com.splendo.kaluga.scientific.unit.ElectricCurrent
import com.splendo.kaluga.scientific.unit.ElectricInductance
import com.splendo.kaluga.scientific.unit.MagneticFlux
import kotlin.jvm.JvmName

@JvmName("inductanceFromFluxAndCurrentDefault")
fun <
    FluxUnit : MagneticFlux,
    CurrentUnit : ElectricCurrent,
    InductanceUnit : ElectricInductance
> InductanceUnit.inductance(
    flux: ScientificValue<MeasurementType.MagneticFlux, FluxUnit>,
    current: ScientificValue<MeasurementType.ElectricCurrent, CurrentUnit>
) = inductance(flux, current, ::DefaultScientificValue)

@JvmName("inductanceFromFluxAndCurrent")
fun <
    FluxUnit : MagneticFlux,
    CurrentUnit : ElectricCurrent,
    InductanceUnit : ElectricInductance,
    Value : ScientificValue<MeasurementType.ElectricInductance, InductanceUnit>
> InductanceUnit.inductance(
    flux: ScientificValue<MeasurementType.MagneticFlux, FluxUnit>,
    current: ScientificValue<MeasurementType.ElectricCurrent, CurrentUnit>,
    factory: (Decimal, InductanceUnit) -> Value
) = byDividing(flux, current, factory)
