package br.com.pix_service.ewallet.infrastructure.annotation.specification;


import br.com.pix_service.ewallet.infrastructure.persistence.SpecificationOperation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ FIELD })
public @interface SpecificationField {

    String property() default "";
    
    String join() default "";

    SpecificationOperation operation() default SpecificationOperation.EQUAL;

}
