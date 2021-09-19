package handler;

import java.lang.reflect.Modifier;

public class Private extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isPrivate(mods)) {
			buf.append("private ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
