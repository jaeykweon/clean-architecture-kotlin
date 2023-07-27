package org.idd.cleanarchitecturekotlin.common

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.Validator

abstract class SelfValidating<T>(
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator
) {
    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    protected fun validateSelf() {
        val violations: Set<ConstraintViolation<T>> = validator.validate(this as T)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}
