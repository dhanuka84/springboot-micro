package org.reactor.k8s.micro.utils.exception;

@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {

	R apply(T t) throws E;
}
