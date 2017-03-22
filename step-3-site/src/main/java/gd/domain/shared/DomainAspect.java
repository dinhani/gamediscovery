package gd.domain.shared;

import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDAspect;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

@GDAspect
@Aspect
public class DomainAspect {

    private static final Logger LOGGER = LogProducer.getLogger(DomainAspect.class);

    //@Around("execution(* gd.domain..*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        // start timer
        long start = System.currentTimeMillis();

        // execute method
        Object returnValue = jp.proceed();

        // stop timer
        long end = System.currentTimeMillis();

        // log
        long diff = end - start;
        if (diff > 10) {
            LOGGER.info(LogMarker.PROFILE, "class={}, method={}, time={}",
                    StringUtils.rightPad(jp.getTarget().getClass().getSimpleName(), 40, " "),
                    StringUtils.rightPad(jp.getSignature().getName(), 20, " "),
                    diff);
        }

        // return method value
        return returnValue;
    }
}
