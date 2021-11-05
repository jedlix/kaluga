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

import com.splendo.kaluga.scientific.MeasurementType
import com.splendo.kaluga.scientific.ScientificValue
import com.splendo.kaluga.scientific.converter.force.force
import com.splendo.kaluga.scientific.unit.Centimeter
import com.splendo.kaluga.scientific.unit.Dyne
import com.splendo.kaluga.scientific.unit.Energy
import com.splendo.kaluga.scientific.unit.Erg
import com.splendo.kaluga.scientific.unit.FootPoundForce
import com.splendo.kaluga.scientific.unit.FootPoundal
import com.splendo.kaluga.scientific.unit.ImperialEnergy
import com.splendo.kaluga.scientific.unit.ImperialLength
import com.splendo.kaluga.scientific.unit.Inch
import com.splendo.kaluga.scientific.unit.InchOunceForce
import com.splendo.kaluga.scientific.unit.Length
import com.splendo.kaluga.scientific.unit.MeasurementSystem
import com.splendo.kaluga.scientific.unit.MetricAndImperialEnergy
import com.splendo.kaluga.scientific.unit.MetricEnergy
import com.splendo.kaluga.scientific.unit.MetricLength
import com.splendo.kaluga.scientific.unit.MetricMultipleUnit
import com.splendo.kaluga.scientific.unit.Newton
import com.splendo.kaluga.scientific.unit.OunceForce
import com.splendo.kaluga.scientific.unit.PoundForce
import com.splendo.kaluga.scientific.unit.Poundal
import kotlin.jvm.JvmName

@JvmName("ergDivCentimeter")
infix operator fun ScientificValue<MeasurementType.Energy, Erg>.div(distance: ScientificValue<MeasurementType.Length, Centimeter>) = Dyne.force(this, distance)
@JvmName("ergMultipleDivCentimeter")
infix operator fun <ErgUnit> ScientificValue<MeasurementType.Energy, ErgUnit>.div(distance: ScientificValue<MeasurementType.Length, Centimeter>) where ErgUnit : Energy, ErgUnit : MetricMultipleUnit<MeasurementSystem.Metric, MeasurementType.Energy, Erg> = Dyne.force(this, distance)
@JvmName("metricAndImperialEnergyDivMetricLength")
infix operator fun <EnergyUnit : MetricAndImperialEnergy, LengthUnit : MetricLength> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = Newton.force(this, distance)
@JvmName("metricEnergyDivMetricLength")
infix operator fun <EnergyUnit : MetricEnergy, LengthUnit : MetricLength> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = Newton.force(this, distance)
@JvmName("footPoundalDivImperialLength")
infix operator fun <LengthUnit : ImperialLength> ScientificValue<MeasurementType.Energy, FootPoundal>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = Poundal.force(this, distance)
@JvmName("ounceForceDivInch")
infix operator fun ScientificValue<MeasurementType.Energy, InchOunceForce>.div(distance: ScientificValue<MeasurementType.Length, Inch>) = OunceForce.force(this, distance)
@JvmName("poundForceDivImperialLength")
infix operator fun <LengthUnit : ImperialLength> ScientificValue<MeasurementType.Force, PoundForce>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = FootPoundForce.energy(this, distance)
@JvmName("metricAndImperialEnergyDivImperialLength")
infix operator fun <EnergyUnit : MetricAndImperialEnergy, LengthUnit : ImperialLength> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = PoundForce.force(this, distance)
@JvmName("imperialEnergyDivImperialLength")
infix operator fun <EnergyUnit : ImperialEnergy, LengthUnit : ImperialLength> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = PoundForce.force(this, distance)
@JvmName("energyDivLength")
infix operator fun <EnergyUnit : Energy, LengthUnit : Length> ScientificValue<MeasurementType.Energy, EnergyUnit>.div(distance: ScientificValue<MeasurementType.Length, LengthUnit>) = Newton.force(this, distance)
