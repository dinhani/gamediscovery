package gd.infrastructure.aspect;

import gd.infrastructure.steriotype.GDProducer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@GDProducer
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectProducer {

}
