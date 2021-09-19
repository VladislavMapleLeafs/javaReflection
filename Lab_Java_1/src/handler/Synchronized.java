package handler;

import java.lang.reflect.Modifier;

public class Synchronized extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isSynchronized(mods)) {
			buf.append("synchronized ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
