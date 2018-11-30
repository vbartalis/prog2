package hu.hnk.binfa.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import hu.hnk.binfa.common.Csomopont;

@Aspect
public class AspectJCode {

	Csomopont p = null;

//	@Before("execution(* hu.hnk.binfa.common.LZWBinFa.egyBitFeldolg(..))")
//	public void before(JoinPoint joinPoint) {
//		System.out.println("Before egyBitFeldolg");
//	}

	/*@AfterReturning(pointcut = "execution(* hu.hnk.binfa.core.LZWBinFa.egyBitFeldolg(..))", returning = "result")
	public void after(JoinPoint joinPoint, Object result) {

		System.out.println("After egyBitFeldolg");
		//System.out.println("Method returned value is : " + result);
		//p = (Csomopont) result;

	}*/

	/*
	 * @Pointcut(value=
	 * "execution(* hu.hnk.binfa.common.LZWBinFa.egyBitFeldolg(..))") public
	 * void egyBitFeldolg() {
	 * 
	 * }
	 * 
	 * @Around("egyBitFeldolg()") public Object myAspect(final
	 * ProceedingJoinPoint pjp) throws Throwable{
	 * 
	 * // retrieve the methods parameter types (static): final Signature
	 * signature = pjp.getStaticPart().getSignature(); if(signature instanceof
	 * MethodSignature){ final MethodSignature ms = (MethodSignature) signature;
	 * final Class<?>[] parameterTypes = ms.getParameterTypes(); for(final
	 * Class<?> pt : parameterTypes){ System.out.println("Parameter type:" +
	 * pt); } }
	 * 
	 * // retrieve the runtime method arguments (dynamic)
	 * pjp.getClass().getMethod(getActual()); for(final Object argument :
	 * pjp.getArgs()){ System.out.println("Parameter value:" + argument); }
	 * 
	 * return pjp.proceed(); }
	 */
}
