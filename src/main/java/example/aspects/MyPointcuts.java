package example.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class MyPointcuts {
  // ex07
  @Pointcut("execution(* get*())")
  public void allGetMethods(){}

  @Pointcut("execution(* add*(..))")
  public void allAddMethods(){}
}
