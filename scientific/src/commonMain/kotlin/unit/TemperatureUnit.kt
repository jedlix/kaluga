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

import com.splendo.kaluga.base.utils.Decimal
import com.splendo.kaluga.base.utils.div
import com.splendo.kaluga.base.utils.minus
import com.splendo.kaluga.base.utils.plus
import com.splendo.kaluga.base.utils.times
import com.splendo.kaluga.base.utils.toDecimal
import com.splendo.kaluga.scientific.PhysicalQuantity
import kotlinx.serialization.Serializable

val MetricAndUkImperialTemperatureUnits: Set<MetricAndUKImperialTemperature> get() = setOf(Kelvin, Celsius)
val USCustomaryTemperatureUnits: Set<USCustomaryTemperature> get() = setOf(Rankine, Fahrenheit)
val TemperatureUnits: Set<Temperature> get() = MetricAndUkImperialTemperatureUnits + USCustomaryTemperatureUnits

@Serializable
sealed class Temperature : AbstractScientificUnit<PhysicalQuantity.Temperature>() {
    override val type = PhysicalQuantity.Temperature
    abstract fun deltaToSIUnitDelta(delta: Decimal): Decimal
    abstract fun deltaFromSIUnitDelta(delta: Decimal): Decimal
}

@Serializable
sealed class MetricAndUKImperialTemperature(override val symbol: String) : Temperature(), MetricAndUKImperialScientificUnit<PhysicalQuantity.Temperature>, MeasurementUsage.UsedInUKImperial {
    override val system = MeasurementSystem.MetricAndUKImperial
}

@Serializable
sealed class USCustomaryTemperature(override val symbol: String) : Temperature(), USCustomaryScientificUnit<PhysicalQuantity.Temperature> {
    override val system = MeasurementSystem.USCustomary
}

@Serializable
object Celsius : MetricAndUKImperialTemperature("°C") {
    override fun toSIUnit(value: Decimal): Decimal = value + Kelvin.KELVIN_FREEZING.toDecimal()
    override fun fromSIUnit(value: Decimal): Decimal = value - Kelvin.KELVIN_FREEZING.toDecimal()
    override fun deltaToSIUnitDelta(delta: Decimal): Decimal = Kelvin.toSIUnit(delta)
    override fun deltaFromSIUnitDelta(delta: Decimal): Decimal = Kelvin.fromSIUnit(delta)
}

@Serializable
object Kelvin : MetricAndUKImperialTemperature("K") {
    internal const val KELVIN_FREEZING = 273.15
    override fun toSIUnit(value: Decimal): Decimal = value
    override fun fromSIUnit(value: Decimal): Decimal = value
    override fun deltaToSIUnitDelta(delta: Decimal): Decimal = toSIUnit(delta)
    override fun deltaFromSIUnitDelta(delta: Decimal): Decimal = fromSIUnit(delta)
}

@Serializable
object Fahrenheit : USCustomaryTemperature("°F") {
    override fun toSIUnit(value: Decimal): Decimal = Rankine.toSIUnit(value + Rankine.RANKINE_FREEZING.toDecimal())
    override fun fromSIUnit(value: Decimal): Decimal = Rankine.fromSIUnit(value) - Rankine.RANKINE_FREEZING.toDecimal()
    override fun deltaToSIUnitDelta(delta: Decimal): Decimal = Rankine.toSIUnit(delta)
    override fun deltaFromSIUnitDelta(delta: Decimal): Decimal = Rankine.fromSIUnit(delta)
}

@Serializable
object Rankine : USCustomaryTemperature("°R") {
    internal const val RANKINE_FREEZING = 459.67
    private const val FAHRENHEIT_INCREASE_PER_CELSIUS_INCREASE = 5.0 / 9.0
    override fun toSIUnit(value: Decimal): Decimal = value * FAHRENHEIT_INCREASE_PER_CELSIUS_INCREASE.toDecimal()
    override fun fromSIUnit(value: Decimal): Decimal = value / FAHRENHEIT_INCREASE_PER_CELSIUS_INCREASE.toDecimal()
    override fun deltaToSIUnitDelta(delta: Decimal): Decimal = toSIUnit(delta)
    override fun deltaFromSIUnitDelta(delta: Decimal): Decimal = fromSIUnit(delta)
}
