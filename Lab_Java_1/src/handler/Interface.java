package handler;

import java.lang.reflect.Modifier;

public class Interface extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isInterface(mods)) {
			buf.append("interface ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
