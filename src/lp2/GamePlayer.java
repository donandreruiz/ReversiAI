package lp2;

public interface GamePlayer<T> {
	public String getName();
	public T getMove(GameState<T> gs);
}
