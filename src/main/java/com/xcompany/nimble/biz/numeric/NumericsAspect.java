/*
 * Copyright (c) 2022.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.numeric;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author xiadong
 * @since 2022/01/12
 **/
@Component
@Aspect
public class NumericsAspect {
    @Around(value = "this(NumericPreprocessor) "
            + " && !execution(private * *(..)) "
            + " && !execution(* *.process(..))"
            + " && !execution(* *.reload(..))"
    )
    public Object anyPublicMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Numerics.readLock();
        try {
            return joinPoint.proceed();
        } finally {
            Numerics.releaseReadLock();
        }
    }
}
