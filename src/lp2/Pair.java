package lp2;

public class Pair<T> {
	private T first;
	private T second;
	
	public Pair(T t1, T t2) {
		first = t1;
		second = t2;
	}
	
	public T getFirst() {
		return first;
	}
	
	public T getSecond() {
		return second;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pair<?>))
			return false;
		Pair<?> p = (Pair<?>)o;
		return first.equals(p.first) && second.equals(p.second);
	}
	
	@Override
	public int hashCode() {
		return first.hashCode() ^ (2*second.hashCode());
	}
	
	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}
}
