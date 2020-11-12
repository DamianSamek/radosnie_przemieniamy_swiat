package pl.edu.wsiz.util;

import java.util.function.Supplier;

public class ObjectUtilsExt {

	public static <T> T get(Supplier<T> resolver) {
		try {
			return resolver.get();
		} catch (NullPointerException e) {
			return null;
		}
	}

}
