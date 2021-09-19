package handler;

import java.lang.reflect.Modifier;

public class Public extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isPublic(mods)) {
			buf.append("public ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
