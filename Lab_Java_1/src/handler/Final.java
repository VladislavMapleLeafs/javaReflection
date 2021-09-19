package handler;

import java.lang.reflect.Modifier;

public class Final extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isFinal(mods)) {
			buf.append("final ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}