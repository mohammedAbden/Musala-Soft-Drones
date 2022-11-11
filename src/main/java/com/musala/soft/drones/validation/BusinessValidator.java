package com.musala.soft.drones.validation;

import com.musala.soft.drones.exception.RuntimeBusinessException;

public interface BusinessValidator<T> {

     void doCheck(T object) throws RuntimeBusinessException;
}
