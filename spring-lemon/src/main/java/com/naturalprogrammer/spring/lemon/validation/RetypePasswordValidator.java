package com.naturalprogrammer.spring.lemon.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * Validator for RetypePassword constraint
 * 
 * @see <a href="http://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html/validator-usingvalidator.html#d0e326">Hibernate Validator</a>
 * @see <a href="http://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html/validator-customconstraints.html#validator-customconstraints-validator">Custom constraints</a>
 * 
 * @author Sanjay Patel
 */
@Component
public class RetypePasswordValidator
implements ConstraintValidator<RetypePassword, RetypePasswordForm> {
	
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean isValid(RetypePasswordForm retypePasswordForm,
		ConstraintValidatorContext context) {
		
		if (!Objects.equals(retypePasswordForm.getPassword(),
				            retypePasswordForm.getRetypePassword())) {
			
			log.debug("Retype password validation failed.");
			
			// Moving the error from form-level to
			// field-level property: retypePassword
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
				"{com.naturalprogrammer.spring.different.passwords}")
				.addPropertyNode("retypePassword").addConstraintViolation();
			
			return false;
			
		}
		
		log.debug("Retype password validation succeeded.");		
		return true;
	}

	@Override
	public void initialize(RetypePassword constraintAnnotation) {
		log.debug("RetypePasswordValidator initialized.");
	}

}
