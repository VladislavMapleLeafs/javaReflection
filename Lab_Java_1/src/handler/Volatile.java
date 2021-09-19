package handler;

import java.lang.reflect.Modifier;

public class Volatile extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isVolatile(mods)) {
			buf.append("volatile ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
