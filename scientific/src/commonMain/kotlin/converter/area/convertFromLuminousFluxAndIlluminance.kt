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

package com.splendo.kaluga.scientific.converter.area

import com.splendo.kaluga.base.utils.Decimal
import com.splendo.kaluga.scientific.DefaultScientificValue
import com.splendo.kaluga.scientific.MeasurementType
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.byDividing
import com.splendo.kaluga.scientific.unit.Area
import com.splendo.kaluga.scientific.unit.Illuminance
import com.splendo.kaluga.scientific.unit.LuminousFlux
import kotlin.jvm.JvmName

@JvmName("areaFromFluxAndIlluminanceDefault")
fun <
    FluxUnit : LuminousFlux,
    AreaUnit : Area,
    IlluminanceUnit : Illuminance
> AreaUnit.area(
    flux: ScientificValue<MeasurementType.LuminousFlux, FluxUnit>,
    illuminance: ScientificValue<MeasurementType.Illuminance, IlluminanceUnit>
) = area(flux, illuminance, ::DefaultScientificValue)

@JvmName("areaFromFluxAndIlluminance")
fun <
    FluxUnit : LuminousFlux,
    AreaUnit : Area,
    IlluminanceUnit : Illuminance,
    Value : ScientificValue<MeasurementType.Area, AreaUnit>
> AreaUnit.area(
    flux: ScientificValue<MeasurementType.LuminousFlux, FluxUnit>,
    illuminance: ScientificValue<MeasurementType.Illuminance, IlluminanceUnit>,
    factory: (Decimal, AreaUnit) -> Value
) = byDividing(flux, illuminance, factory)
