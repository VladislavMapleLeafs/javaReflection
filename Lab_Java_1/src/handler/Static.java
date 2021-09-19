package handler;

import java.lang.reflect.Modifier;

public class Static extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isStatic(mods)) {
			buf.append("static ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
