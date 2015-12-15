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
package org.sosy_lab.solver.cvc4;

import edu.nyu.acsys.CVC4.Expr;
import edu.nyu.acsys.CVC4.ExprManager;
import edu.nyu.acsys.CVC4.Type;

import org.sosy_lab.solver.basicimpl.AbstractUnsafeFormulaManager;
import org.sosy_lab.solver.basicimpl.FormulaCreator;

import java.util.List;

public class CVC4UnsafeFormulaManager
    extends AbstractUnsafeFormulaManager<Expr, Type, CVC4Environment> {

  private final ExprManager exprManager;

  protected CVC4UnsafeFormulaManager(FormulaCreator<Expr, Type, CVC4Environment> pCreator) {
    super(pCreator);
    exprManager = pCreator.getEnv().getExprManager();
  }

  @Override
  protected boolean isAtom(Expr pT) {
    return pT.isConst() || pT.isVariable();
  }

  @Override
  protected int getArity(Expr pT) {
    return (int) pT.getNumChildren();
  }

  @Override
  protected Expr getArg(Expr pT, int pN) {
    return pT.getChild(pN);
  }

  @Override
  protected boolean isVariable(Expr pT) {
    return pT.isVariable();
  }

  @Override
  protected boolean isFreeVariable(Expr pT) {
    return pT.isVariable(); // TODO change when quantifiers are implemented
  }

  @Override
  protected boolean isBoundVariable(Expr pT) {
    return false; // TODO change when quantifiers are implemented
  }

  @Override
  protected boolean isQuantification(Expr pT) {
    return false; // TODO change when quantifiers are implemented
  }

  @Override
  protected Expr getQuantifiedBody(Expr pT) {
    return null; // TODO change when quantifiers are implemented
  }

  @Override
  protected Expr replaceQuantifiedBody(Expr pF, Expr pBody) {
    throw new UnsupportedOperationException("Quantifiers not implemented for CVC4");
  }

  @Override
  protected boolean isNumber(Expr pT) {
    return isAtom(pT) && pT.getType().isInteger(); // TODO float, rationals?
  }

  @Override
  protected boolean isUF(Expr pT) {
    return pT.getType().isFunction();
  }

  @Override
  protected String getName(Expr pT) {
    return pT.toString();
  }

  @Override
  protected Expr replaceArgsAndName(Expr pT, String pNewName, List<Expr> pNewArgs) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Expr replaceArgs(Expr pT, List<Expr> pNewArgs) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<? extends Expr> splitNumeralEqualityIfPossible(Expr pF) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Expr substitute(Expr pExpr, List<Expr> pSubstituteFrom, List<Expr> pSubstituteTo) {
    // TODO Auto-generated method stub
    return null;
  }
}