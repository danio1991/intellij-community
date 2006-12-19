package com.intellij.psi.scope.processor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.scope.BaseScopeProcessor;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.SmartList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ik
 * Date: 13.02.2003
 * Time: 15:21:27
 * To change this template use Options | File Templates.
 */
public class FilterScopeProcessor<T> extends BaseScopeProcessor{
  protected final List<T> myResults;
  private PsiElement myCurrentDeclarationHolder;
  private final ElementFilter myFilter;
  private final PsiScopeProcessor myProcessor;

  public FilterScopeProcessor(ElementFilter filter, PsiScopeProcessor processor, List<T> container){
    myFilter = filter;
    myProcessor = processor;
    myResults = container;
  }

  public FilterScopeProcessor(ElementFilter filter, List<T> container){
    this(filter, null, container);
  }

  public FilterScopeProcessor(ElementFilter filter, PsiScopeProcessor proc){
    this(filter, proc, new SmartList<T>());
  }

  public FilterScopeProcessor(ElementFilter filter){
    this(filter, null, new SmartList<T>());
  }

  public void handleEvent(Event event, Object associated){
    if(myProcessor != null){
      myProcessor.handleEvent(event, associated);
    }
    if(event == Event.SET_DECLARATION_HOLDER && associated instanceof PsiElement){
      myCurrentDeclarationHolder = (PsiElement)associated;
    }
  }

  public boolean execute(PsiElement element, PsiSubstitutor substitutor){
    if (myFilter.isAcceptable(element, myCurrentDeclarationHolder)){
      if(myProcessor != null){
        return myProcessor.execute(element, substitutor);
      }
      add(element, substitutor);
    }
    return true;
  }

  protected void add(PsiElement element, PsiSubstitutor substitutor){
    myResults.add((T)element);
  }

  public List<T> getResults(){
    return myResults;
  }
}
