package handler;

import java.lang.reflect.Modifier;

public class Transient extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isTransient(mods)) {
			buf.append("transient ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
