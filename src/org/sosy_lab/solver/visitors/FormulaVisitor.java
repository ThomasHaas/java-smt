/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2015  Dirk Beyer
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
package org.sosy_lab.solver.visitors;

import com.google.common.base.Function;

import java.util.List;

import org.sosy_lab.solver.api.BooleanFormula;
import org.sosy_lab.solver.api.Formula;
import org.sosy_lab.solver.api.FormulaManager;
import org.sosy_lab.solver.api.FormulaType;
import org.sosy_lab.solver.api.UfDeclaration;


/**
 * Visitor iterating through entire formula.
 */
public abstract class FormulaVisitor<R> {
  private final FormulaManager fmgr;

  protected FormulaVisitor(FormulaManager pFmgr) {
    fmgr = pFmgr;
  }

  public final R visit(Formula f) {
    return fmgr.visit(this, f);
  }

  public abstract R visitFreeVariable(String name, FormulaType<?> type);
  public abstract R visitBoundVariable(String name, FormulaType<?> type);
  public abstract R visitNumeral(String numeral, FormulaType<?> type);
  public abstract R visitUF(
      String functionName,
      UfDeclaration<?> declaration,
      List<Formula> args);
  public abstract R visitFunction(
      String functionName,
      List<Formula> args,
      FormulaType<?> type,
      Function<List<Formula>, Formula> newApplicationConstructor);
  public abstract R visitForAll(List<Formula> variables, BooleanFormula body);
  public abstract R visitExists(List<Formula> variables, BooleanFormula body);
}