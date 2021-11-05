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
import com.splendo.kaluga.base.utils.minus
import com.splendo.kaluga.base.utils.plus
import com.splendo.kaluga.base.utils.times
import com.splendo.kaluga.base.utils.toDecimal
import com.splendo.kaluga.base.utils.toDouble
import com.splendo.kaluga.scientific.unit.ScientificUnit
import com.splendo.kaluga.scientific.unit.convert
import kotlinx.serialization.Serializable

interface ScientificValue<Type : PhysicalQuantity, Unit : ScientificUnit<Type>> : Comparable<ScientificValue<Type, *>>, com.splendo.kaluga.base.utils.Serializable {
    val value: Number
    val unit: Unit

    override fun compareTo(other: ScientificValue<Type, *>): Int = unit.toSIUnit(decimalValue).compareTo(other.unit.toSIUnit(other.decimalValue))

    val decimalValue: Decimal get() = value.toDecimal()
}

@Serializable
data class DefaultScientificValue<Type : PhysicalQuantity, Unit : ScientificUnit<Type>>(
    override val value: Double,
    override val unit: Unit
) : ScientificValue<Type, Unit> {
    constructor(value: Decimal, unit: Unit) : this(value.toDouble(), unit)
}

// Creation

operator fun <
    Type : PhysicalQuantity,
    UnitType : ScientificUnit<Type>
    > Number.invoke(unit: UnitType) = this.toDecimal()(unit)
operator fun <
    Type : PhysicalQuantity,
    UnitType : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, UnitType>
    > Number.invoke(unit: UnitType, factory: (Decimal, UnitType) -> ValueType) = this.toDecimal()(unit, factory)

operator fun <
    Type : PhysicalQuantity,
    UnitType : ScientificUnit<Type>
    > Decimal.invoke(unit: UnitType) = this(unit, ::DefaultScientificValue)
operator fun <
    Type : PhysicalQuantity,
    UnitType : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, UnitType>
    > Decimal.invoke(unit: UnitType, factory: (Decimal, UnitType) -> ValueType) = factory(this, unit)

// Conversion

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    TargetUnit : ScientificUnit<Type>,
    TargetValue : ScientificValue<Type, TargetUnit>
    > ScientificValue<Type, Unit>.convert(
    target: TargetUnit,
    factory: (Decimal, TargetUnit) -> TargetValue
): TargetValue = factory(convertValue(target), target)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    TargetUnit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.convert(target: TargetUnit) = convert(target, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    TargetUnit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.convertValue(target: TargetUnit): Decimal {
    return unit.convert(decimalValue, target)
}

// Calculation

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.plus(factor: Number) = this + factor.toDecimal()

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Number.plus(unit: ScientificValue<Type, Unit>) = toDecimal() + unit

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.plus(factor: Decimal) = plus(factor, ::DefaultScientificValue)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Decimal.plus(unit: ScientificValue<Type, Unit>) = unit.plus(this, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > ScientificValue<Type, Unit>.plus(
    factor: Decimal,
    factory: (Decimal, Unit) -> ValueType
) = factory(decimalValue + factor, unit)

infix operator fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>
    > ScientificValue<Type, Left>.plus(right: ScientificValue<Type, Right>) = unit.plus(this, right, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Unit.plus(
    left: ScientificValue<Type, Left>,
    right: ScientificValue<Type, Right>,
    factory: (Decimal, Unit) -> ValueType
) = byAdding(left, right, factory)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.minus(value: Number) = this - value.toDecimal()

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Number.minus(unit: ScientificValue<Type, Unit>) = toDecimal() - unit

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.minus(value: Decimal) = minus(value, ::DefaultScientificValue)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Decimal.minus(unit: ScientificValue<Type, Unit>) = minus(unit, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Decimal.minus(
    value: ScientificValue<Type, Unit>,
    factory: (Decimal, Unit) -> ValueType
) = factory(this - value.decimalValue, value.unit)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > ScientificValue<Type, Unit>.minus(
    value: Decimal,
    factory: (Decimal, Unit) -> ValueType
) = factory(decimalValue - value, unit)

infix operator fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>
    > ScientificValue<Type, Left>.minus(right: ScientificValue<Type, Right>) = unit.minus(this, right, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Unit.minus(
    left: ScientificValue<Type, Left>,
    right: ScientificValue<Type, Right>,
    factory: (Decimal, Unit) -> ValueType
) = bySubtracting(left, right, factory)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.times(factor: Number) = this * factor.toDecimal()

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Number.times(unit: ScientificValue<Type, Unit>) = toDecimal() * unit

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.times(factor: Decimal) = times(factor, ::DefaultScientificValue)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Decimal.times(unit: ScientificValue<Type, Unit>) = unit.times(this, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > ScientificValue<Type, Unit>.times(
    factor: Decimal,
    factory: (Decimal, Unit) -> ValueType
) = factory(decimalValue * factor, unit)

infix operator fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>
    > ScientificValue<Type, Left>.times(right: ScientificValue<Type, Right>) = unit.times(this, right, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Unit.times(
    left: ScientificValue<Type, Left>,
    right: ScientificValue<Type, Right>,
    factory: (Decimal, Unit) -> ValueType
) = byMultiplying(left, right, factory)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.div(factor: Number) = this / factor.toDecimal()

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Number.div(unit: ScientificValue<Type, Unit>) = toDecimal() / unit

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > ScientificValue<Type, Unit>.div(factor: Decimal) = div(factor, ::DefaultScientificValue)

infix operator fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>
    > Decimal.div(unit: ScientificValue<Type, Unit>) = div(unit, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Decimal.div(
    factor: ScientificValue<Type, Unit>,
    factory: (Decimal, Unit) -> ValueType
) = factory(this / factor.decimalValue, factor.unit)

fun <
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > ScientificValue<Type, Unit>.div(
    factor: Decimal,
    factory: (Decimal, Unit) -> ValueType
) = factory(decimalValue / factor, unit)

infix operator fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>
    > ScientificValue<Type, Left>.div(right: ScientificValue<Type, Right>) = unit.div(this, right, ::DefaultScientificValue)

fun <
    Type : PhysicalQuantity,
    Left : ScientificUnit<Type>,
    Right : ScientificUnit<Type>,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Unit.div(
    left: ScientificValue<Type, Left>,
    right: ScientificValue<Type, Right>,
    factory: (Decimal, Unit) -> ValueType
) = byDividing(left, right, factory)

internal fun <
    TargetType : PhysicalQuantity,
    Unit : ScientificUnit<TargetType>,
    ValueType : ScientificValue<TargetType, Unit>,
    LeftType : PhysicalQuantity,
    LeftUnit : ScientificUnit<LeftType>,
    RightType : PhysicalQuantity,
    RightUnit : ScientificUnit<RightType>
    > Unit.byAdding(
    left: ScientificValue<LeftType, LeftUnit>,
    right: ScientificValue<RightType, RightUnit>,
    factory: (Decimal, Unit) -> ValueType
) = fromSIUnit(left.unit.toSIUnit(left.decimalValue) + right.unit.toSIUnit(right.decimalValue))(this, factory)

internal fun <
    TargetType : PhysicalQuantity,
    Unit : ScientificUnit<TargetType>,
    ValueType : ScientificValue<TargetType, Unit>,
    LeftType : PhysicalQuantity,
    LeftUnit : ScientificUnit<LeftType>,
    RightType : PhysicalQuantity,
    RightUnit : ScientificUnit<RightType>
    > Unit.bySubtracting(
    left: ScientificValue<LeftType, LeftUnit>,
    right: ScientificValue<RightType, RightUnit>,
    factory: (Decimal, Unit) -> ValueType
) = fromSIUnit(left.unit.toSIUnit(left.decimalValue) - right.unit.toSIUnit(right.decimalValue))(this, factory)

internal fun <
    TargetType : PhysicalQuantity,
    Unit : ScientificUnit<TargetType>,
    ValueType : ScientificValue<TargetType, Unit>,
    NominatorType : PhysicalQuantity,
    NominatorUnit : ScientificUnit<NominatorType>,
    DividerType : PhysicalQuantity,
    DividerUnit : ScientificUnit<DividerType>
    > Unit.byDividing(
    nominator: ScientificValue<NominatorType, NominatorUnit>,
    divider: ScientificValue<DividerType, DividerUnit>,
    factory: (Decimal, Unit) -> ValueType
) = fromSIUnit(nominator.unit.toSIUnit(nominator.decimalValue) / divider.unit.toSIUnit(divider.decimalValue))(this, factory)

internal fun <
    TargetType : PhysicalQuantity,
    Unit : ScientificUnit<TargetType>,
    ValueType : ScientificValue<TargetType, Unit>,
    LeftType : PhysicalQuantity,
    LeftUnit : ScientificUnit<LeftType>,
    RightType : PhysicalQuantity,
    RightUnit : ScientificUnit<RightType>
    > Unit.byMultiplying(
    left: ScientificValue<LeftType, LeftUnit>,
    right: ScientificValue<RightType, RightUnit>,
    factory: (Decimal, Unit) -> ValueType
) = fromSIUnit(left.unit.toSIUnit(left.decimalValue) * right.unit.toSIUnit(right.decimalValue))(this, factory)

internal fun <
    InverseType : PhysicalQuantity,
    InverseUnit : ScientificUnit<InverseType>,
    Type : PhysicalQuantity,
    Unit : ScientificUnit<Type>,
    ValueType : ScientificValue<Type, Unit>
    > Unit.byInverting(
    inverse: ScientificValue<InverseType, InverseUnit>,
    factory: (Decimal, Unit) -> ValueType
) = fromSIUnit(1.0.toDecimal() / inverse.unit.toSIUnit(inverse.decimalValue))(this, factory)
