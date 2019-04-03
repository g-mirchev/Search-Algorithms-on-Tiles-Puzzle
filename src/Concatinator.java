import java.lang.reflect.Array;

public class Concatinator {
	
	/*
	 * These methods are used to concatenate two arrays.
	 * It's based on a method I found online the original
	 * can be found on Stack Overflow:
	 * https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
	 */
	public static char[] concat(char[] a, char[] b) {
	   int al = a.length;
	   int bl = b.length;
	   char[] c = new char[al+bl];
	   System.arraycopy(a, 0, c, 0, al);
	   System.arraycopy(b, 0, c, al, bl);
	   return c;
	}
	
	public static <T> T[] concatenate (T[] a, T[] b) {
	    int al = a.length;
	    int bl = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), al+bl);
	    System.arraycopy(a, 0, c, 0, al);
	    System.arraycopy(b, 0, c, al, bl);
	    return c;
	}
}
