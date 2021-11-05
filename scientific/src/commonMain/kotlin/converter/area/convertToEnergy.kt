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

import com.splendo.kaluga.scientific.PhysicalQuantity
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.converter.surfaceTension.times
import com.splendo.kaluga.scientific.unit.Area
import com.splendo.kaluga.scientific.unit.ImperialArea
import com.splendo.kaluga.scientific.unit.ImperialSurfaceTension
import com.splendo.kaluga.scientific.unit.MetricArea
import com.splendo.kaluga.scientific.unit.MetricSurfaceTension
import com.splendo.kaluga.scientific.unit.SquareCentimeter
import com.splendo.kaluga.scientific.unit.SquareInch
import com.splendo.kaluga.scientific.unit.SurfaceTension
import com.splendo.kaluga.scientific.unit.UKImperialSurfaceTension
import com.splendo.kaluga.scientific.unit.USCustomarySurfaceTension
import kotlin.jvm.JvmName

@JvmName("squareCentimeterTimesMetricSurfaceTension")
infix operator fun ScientificValue<PhysicalQuantity.Area, SquareCentimeter>.times(surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, MetricSurfaceTension>) =
    surfaceTension * this

@JvmName("metricAreaTimesMetricSurfaceTension")
infix operator fun <AreaUnit : MetricArea> ScientificValue<PhysicalQuantity.Area, AreaUnit>.times(
    surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, MetricSurfaceTension>
) = surfaceTension * this

@JvmName("squareInchTimesImperialSurfaceTension")
infix operator fun ScientificValue<PhysicalQuantity.Area, SquareInch>.times(surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, ImperialSurfaceTension>) =
    surfaceTension * this

@JvmName("squareInchTimesUKImperialSurfaceTension")
infix operator fun ScientificValue<PhysicalQuantity.Area, SquareInch>.times(surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, UKImperialSurfaceTension>) =
    surfaceTension * this

@JvmName("squareInchTimesUSCustomarySurfaceTension")
infix operator fun ScientificValue<PhysicalQuantity.Area, SquareInch>.times(surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, USCustomarySurfaceTension>) =
    surfaceTension * this

@JvmName("imperialAreaTimesImperialSurfaceTensionTimes")
infix operator fun <AreaUnit : ImperialArea> ScientificValue<PhysicalQuantity.Area, AreaUnit>.times(
    surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, ImperialSurfaceTension>
) = surfaceTension * this

@JvmName("imperialAreaTimesUKImperialSurfaceTension")
infix operator fun <AreaUnit : ImperialArea> ScientificValue<PhysicalQuantity.Area, AreaUnit>.times(
    surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, UKImperialSurfaceTension>
) = surfaceTension * this

@JvmName("imperialAreaTimesUSCustomarySurfaceTension")
infix operator fun <AreaUnit : ImperialArea> ScientificValue<PhysicalQuantity.Area, AreaUnit>.times(
    surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, USCustomarySurfaceTension>
) = surfaceTension * this

@JvmName("areTimesSurfaceTension")
infix operator fun <SurfaceTensionUnit : SurfaceTension, AreaUnit : Area> ScientificValue<PhysicalQuantity.Area, AreaUnit>.times(
    surfaceTension: ScientificValue<PhysicalQuantity.SurfaceTension, SurfaceTensionUnit>
) = surfaceTension * this
