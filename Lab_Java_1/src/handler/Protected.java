package handler;

import java.lang.reflect.Modifier;

public class Protected extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isProtected(mods)) {
			buf.append("protected ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}