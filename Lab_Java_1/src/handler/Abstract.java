package handler;

import java.lang.reflect.Modifier;

public class Abstract extends Handler {

	@Override
	void writeModifier(int mods, StringBuffer buf) {
		if(Modifier.isAbstract(mods)) {
			buf.append("abstract ");
		}
		if(next != null) {
			next.writeModifier(mods, buf);
		}		
	}
}
