/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sosy_lab.java_smt.api;

import java.math.BigInteger;
import org.sosy_lab.java_smt.api.NumeralFormula.IntegerFormula;

/**
 * Interface which operates over {@link IntegerFormula}s.
 *
 * <p>Integer formulas always take integral formulas as arguments.
 */
public interface IntegerFormulaManager
    extends NumeralFormulaManager<IntegerFormula, IntegerFormula> {

  /** Create a term representing the constraint {@code number1 == number2 (mod n)}. */
  BooleanFormula modularCongruence(IntegerFormula number1, IntegerFormula number2, BigInteger n);

  /** Create a term representing the constraint {@code number1 == number2 (mod n)}. */
  BooleanFormula modularCongruence(IntegerFormula number1, IntegerFormula number2, long n);

  IntegerFormula modulo(IntegerFormula number1, IntegerFormula number2);

  @Override
  default FormulaType<IntegerFormula> getFormulaType() {
    return FormulaType.IntegerType;
  }
}
