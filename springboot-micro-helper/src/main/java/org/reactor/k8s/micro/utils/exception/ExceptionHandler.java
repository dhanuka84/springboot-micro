package org.reactor.k8s.micro.utils.exception;

import java.util.function.Consumer;
import java.util.function.Function;


public class ExceptionHandler {

	public static <Target> Consumer<Target> handleCheckedExceptionConsumer(
			CheckedExceptionHandlerConsumer<Target, Exception> handlerConsumer) {
		return obj -> {
			try {
				handlerConsumer.accept(obj);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		};
	}
	
	public static <Target, ExObj extends Exception> Consumer<Target> handleGenericException(Consumer<Target> targetConsumer,
			Class<ExObj> exObjClass) {
		return obj -> {
			try {
				targetConsumer.accept(obj);
			} catch (Exception ex) {
				try {
					ExObj exObj = exObjClass.cast(ex);
					System.out.println("exception : " + exObj.getMessage());
				} catch (ClassCastException ecx) {
					throw ex;
				}
			}
		};
	}
	
	public static <T, R, E extends Exception> Function<T, R> wrapper(FunctionWithException<T, R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

}
