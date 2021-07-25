package org.reactor.k8s.micro.exception;

import static org.reactor.k8s.micro.utils.exception.ExceptionHandler.*;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ExceptionHandlingTest {
	public static void main(String[] args) {

		List<String> list = Arrays.asList("44", "373", "xyz");
		List<Integer> list1 = Arrays.asList(1, 0);
		// list1.forEach(handleGenericException(s->
		// System.out.println(10/s),ArithmeticException.class));

		list.forEach(handleGenericException(s -> System.out.println(Integer.parseInt(s)), NumberFormatException.class));

		// List<Integer> intList =
		// list.stream().map(Integer::parseInt).collect(Collectors.toList());
		// System.out.println(intList);
		List<Integer> intList = list.stream().map(wrapper(s -> Integer.parseInt(s))).collect(Collectors.toList());

		// handle exception for checkedException
		List<Integer> list2 = Arrays.asList(1, 0);

		// List<Integer> intList =
		// list.stream().map(Integer::parseInt).collect(Collectors.toList());

		list2.forEach(handleCheckedExceptionConsumer(i -> {
			Thread.sleep(i);
			// throws InterruptedException
			System.out.println(10);
		}));
	}
	// approach -2

	public static void printList(String s) {
		try {
			System.out.println(Integer.parseInt(s));
		} catch (Exception ex) {
			System.out.println("exception : " + ex.getMessage());
		}
	}

	static Consumer<String> handleExceptionIfAny(Consumer<String> payload) {
		return obj -> {
			try {
				payload.accept(obj);
			} catch (Exception ex) {
				System.out.println("exception : " + ex.getMessage());
			}
		};
	}

	

	String encodedAddressUsingWrapper(String... address) {
		return Arrays.stream(address).map(wrapper(s -> URLEncoder.encode(s, "UTF-8"))).collect(Collectors.joining(","));
	}

}
