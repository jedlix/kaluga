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

package com.splendo.kaluga.scientific.converter.energy

import com.splendo.kaluga.scientific.Energy
import com.splendo.kaluga.scientific.Erg
import com.splendo.kaluga.scientific.ImperialEnergy
import com.splendo.kaluga.scientific.ImperialSurfaceTension
import com.splendo.kaluga.scientific.InchOunceForce
import com.splendo.kaluga.scientific.InchPoundForce
import com.splendo.kaluga.scientific.MeasurementSystem
import com.splendo.kaluga.scientific.MeasurementType
import com.splendo.kaluga.scientific.MetricAndImperialEnergy
import com.splendo.kaluga.scientific.MetricEnergy
import com.splendo.kaluga.scientific.MetricMultipleUnit
import com.splendo.kaluga.scientific.MetricSurfaceTension
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.SquareCentimeter
import com.splendo.kaluga.scientific.SquareFoot
import com.splendo.kaluga.scientific.SquareInch
import com.splendo.kaluga.scientific.SquareMeter
import com.splendo.kaluga.scientific.SurfaceTension
import com.splendo.kaluga.scientific.UKImperialSurfaceTension
import com.splendo.kaluga.scientific.USCustomarySurfaceTension
import com.splendo.kaluga.scientific.converter.area.area
import kotlin.jvm.JvmName

@JvmName("ergDivMetricSurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, Erg>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, MetricSurfaceTension>) = SquareCentimeter.area(this, surfaceTension)
@JvmName("ergMultipleDivMetricSurfaceTension")
infix operator fun <ErgUnit> ScientificValue<MeasurementType.Energy, ErgUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, MetricSurfaceTension>) where ErgUnit : Energy, ErgUnit : MetricMultipleUnit<MeasurementSystem.Metric, MeasurementType.Energy, Erg> = SquareCentimeter.area(this, surfaceTension)
@JvmName("metricAndImperialEnergyDivMetricSurfaceTension")
infix operator fun <EnergyUnit : MetricAndImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, MetricSurfaceTension>) = SquareMeter.area(this, surfaceTension)
@JvmName("metricEnergyDivMetricSurfaceTension")
infix operator fun <EnergyUnit : MetricEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, MetricSurfaceTension>) = SquareMeter.area(this, surfaceTension)
@JvmName("inchPoundForceDivImperialSurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchPoundForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, ImperialSurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("inchPoundForceDivUKImperialSurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchPoundForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, UKImperialSurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("inchPoundForceDivUSCustomarySurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchPoundForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, USCustomarySurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("inchOunceForceDivImperialSurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchOunceForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, ImperialSurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("inchOunceForceDivUKImperialSurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchOunceForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, UKImperialSurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("inchOunceForceDivUSCustomarySurfaceTension")
infix operator fun ScientificValue<MeasurementType.Energy, InchOunceForce>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, USCustomarySurfaceTension>) = SquareInch.area(this, surfaceTension)
@JvmName("metricAndImperialEnergyDivImperialSurfaceTension")
infix operator fun <EnergyUnit : MetricAndImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, ImperialSurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("metricAndImperialEnergyDivUKImperialSurfaceTension")
infix operator fun <EnergyUnit : MetricAndImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, UKImperialSurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("metricAndImperialEnergyDivUSCustomarySurfaceTension")
infix operator fun <EnergyUnit : MetricAndImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, USCustomarySurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("imperialEnergyDivImperialSurfaceTension")
infix operator fun <EnergyUnit : ImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, ImperialSurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("imperialEnergyDivUKImperialSurfaceTension")
infix operator fun <EnergyUnit : ImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, UKImperialSurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("imperialEnergyDivUSCustomarySurfaceTension")
infix operator fun <EnergyUnit : ImperialEnergy> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, USCustomarySurfaceTension>) = SquareFoot.area(this, surfaceTension)
@JvmName("energyDivSurfaceTension")
infix operator fun <EnergyUnit : Energy, SurfaceTensionUnit : SurfaceTension> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(surfaceTension: ScientificValue<MeasurementType.SurfaceTension, SurfaceTensionUnit>) = SquareMeter.area(this, surfaceTension)
