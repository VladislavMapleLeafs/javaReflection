package handler;

public abstract class Handler {
	Handler next;
	abstract void writeModifier(int mods, StringBuffer buf);
}