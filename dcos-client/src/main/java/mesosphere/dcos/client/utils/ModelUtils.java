package mesosphere.dcos.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelUtils.
 */
public class ModelUtils {
	
	/** The Constant GSON. */
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	/**
	 * To string.
	 *
	 * @param o the o
	 * @return the string
	 */
	public static String toString(Object o) {
		return GSON.toJson(o);
	}
}
