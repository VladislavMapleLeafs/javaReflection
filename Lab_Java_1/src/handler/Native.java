package handler;

import java.lang.reflect.Modifier;

public class Native extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isNative(mods)) {
			buf.append("native ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
