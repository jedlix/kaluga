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

package com.splendo.kaluga.scientific

import com.splendo.kaluga.base.utils.Decimal
import com.splendo.kaluga.base.utils.div
import com.splendo.kaluga.base.utils.times
import com.splendo.kaluga.base.utils.toDecimal
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName

@Serializable
sealed class Speed : AbstractScientificUnit<MeasurementType.Speed>() {
    abstract val distance: Length
    abstract val per: Time
    override val type = MeasurementType.Speed
    override val symbol: String by lazy { "${distance.symbol} / ${per.symbol}" }
    override fun fromSIUnit(value: Decimal): Decimal = distance.fromSIUnit(value) * per.convert(1.0.toDecimal(), Second)
    override fun toSIUnit(value: Decimal): Decimal = distance.toSIUnit(value) / per.convert(1.0.toDecimal(), Second)
}

@Serializable
data class MetricSpeed(override val distance: MetricLength, override val per: Time) : Speed(), MetricScientificUnit<MeasurementType.Speed> {
    override val system = MeasurementSystem.Metric
}

@Serializable
data class ImperialSpeed(override val distance: ImperialLength, override val per: Time) : Speed(), CommonImperialScientificUnit<MeasurementType.Speed> {
    override val system = MeasurementSystem.CommonImperial
}

infix fun MetricLength.per(time: Time) = MetricSpeed(this, time)
infix fun ImperialLength.per(time: Time) = ImperialSpeed(this, time)
infix fun Length.per(time: Time): Speed = when (this) {
    is MetricLength -> this per time
    is ImperialLength -> this per time
}

@JvmName("metricLengthDivTime")
operator fun <
    LengthUnit : MetricLength,
    > ScientificValue<
    MeasurementType.Length,
    LengthUnit,
    >.div(time: ScientificValue<MeasurementType.Time, Time>): ScientificValue<MeasurementType.Speed, MetricSpeed> {
    val speedUnit = unit per time.unit
    return ScientificValue(value / time.value, speedUnit)
}

@JvmName("imperialLengthDivTime")
operator fun <
    LengthUnit : ImperialLength,
    > ScientificValue<
    MeasurementType.Length,
    LengthUnit,
    >.div(time: ScientificValue<MeasurementType.Time, Time>): ScientificValue<MeasurementType.Speed, ImperialSpeed> {
    val speedUnit = unit per time.unit
    return ScientificValue(value / time.value, speedUnit)
}

@JvmName("lengthDivTime")
operator fun <
    LengthUnit : Length
    > ScientificValue<
MeasurementType.Length,
LengthUnit,
>.div(time: ScientificValue<MeasurementType.Time, Time>): ScientificValue<MeasurementType.Speed, Speed> {
    val speedUnit = unit per time.unit
    return ScientificValue(value / time.value, speedUnit)
}

@JvmName("metricSpeedTimesTime")
infix operator fun ScientificValue<MeasurementType.Speed, MetricSpeed>.times(time: ScientificValue<MeasurementType.Time, Time>) : ScientificValue<MeasurementType.Length, MetricLength> {
        return ScientificValue(value * time.convertValue(unit.per), unit.distance)
    }

@JvmName("imperialSpeedTimesTime")
infix operator fun  ScientificValue<MeasurementType.Speed, ImperialSpeed>.times(time: ScientificValue<MeasurementType.Time, Time>) : ScientificValue<MeasurementType.Length, ImperialLength> {
    return ScientificValue(value * time.convertValue(unit.per), unit.distance)
}

@JvmName("speedTimesTime")
infix operator fun  ScientificValue<MeasurementType.Speed, Speed>.times(time: ScientificValue<MeasurementType.Time, Time>) : ScientificValue<MeasurementType.Length, Length> {
    return ScientificValue(value * time.convertValue(unit.per), unit.distance)
}

@JvmName("lengthDivSpeed")
infix operator fun <
    LengthUnit : Length,
    SpeedUnit : Speed
    >  ScientificValue<MeasurementType.Length, LengthUnit>.div(speed: ScientificValue<MeasurementType.Speed, SpeedUnit>) : ScientificValue<MeasurementType.Time, Time> {
    return ScientificValue(convertValue(speed.unit.distance) / speed.value, speed.unit.per)
}
