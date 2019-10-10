/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2019  Dirk Beyer
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
package org.sosy_lab.java_smt.solvers.yices2;

import static org.sosy_lab.java_smt.solvers.yices2.Yices2NativeApi.yices_parse_term;
import static org.sosy_lab.java_smt.solvers.yices2.Yices2NativeApi.yices_subst_term;
import static org.sosy_lab.java_smt.solvers.yices2.Yices2NativeApi.yices_term_constructor;
import static org.sosy_lab.java_smt.solvers.yices2.Yices2NativeApi.yices_term_to_string;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.sosy_lab.common.Appender;
import org.sosy_lab.common.Appenders;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.Formula;
import org.sosy_lab.java_smt.api.FormulaType;
import org.sosy_lab.java_smt.basicimpl.AbstractFormulaManager;

public class Yices2FormulaManager extends AbstractFormulaManager<Integer, Integer, Long, Integer> {

  protected Yices2FormulaManager(
      Yices2FormulaCreator pFormulaCreator,
      Yices2UFManager pFunctionManager,
      Yices2BooleanFormulaManager pBooleanManager,
      Yices2IntegerFormulaManager pIntegerManager,
      Yices2RationalFormulaManager pRationalManager,
      Yices2BitvectorFormulaManager pBitvectorManager) {
    super(
        pFormulaCreator,
        pFunctionManager,
        pBooleanManager,
        pIntegerManager,
        pRationalManager,
        pBitvectorManager,
        null,
        null,
        null);
  }

  static Integer getYicesTerm(Formula pT) {
    return ((Yices2Formula) pT).getTerm();
  }

  @Override
  public BooleanFormula parse(String pS) throws IllegalArgumentException {
    // TODO Might expect Yices input language instead of smt-lib2 notation
    return getFormulaCreator().encapsulateBoolean(yices_parse_term(pS));
  }

  @Override
  public Appender dumpFormula(final Integer formula) {
    assert getFormulaCreator().getFormulaType(formula) == FormulaType.BooleanType
        : "Only BooleanFormulas may be dumped";

    return new Appenders.AbstractAppender() {

      @Override
      public void appendTo(Appendable out) throws IOException {
        // TODO add function declarations
        // TODO fold formula to avoid exp. overhead
        out.append("(assert " + yices_term_to_string(formula) + ")");
      }
    };
  }

  @Override
  public <T extends Formula> T
      substitute(final T f, final Map<? extends Formula, ? extends Formula> fromToMapping) {
    int[] changeFrom = new int[fromToMapping.size()];
    int[] changeTo = new int[fromToMapping.size()];
    int idx = 0;
    for (Entry<? extends Formula, ? extends Formula> e : fromToMapping.entrySet()) {
      changeFrom[idx] = extractInfo(e.getKey());
      changeTo[idx] = extractInfo(e.getValue());
      idx++;
    }
    for (int t : changeFrom) {
      System.out.println("Term: " + yices_term_to_string(t));
      System.out.println("Term Constructor: " + yices_term_constructor(t));
    }
    FormulaType<T> type = getFormulaType(f);
    return getFormulaCreator().encapsulate(
        type,
        yices_subst_term(changeFrom.length, changeFrom, changeTo, extractInfo(f)));
  }
}