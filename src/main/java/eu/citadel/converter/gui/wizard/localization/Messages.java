package eu.citadel.converter.gui.wizard.localization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import eu.citadel.converter.gui.wizard.domain.Pair;

public class Messages {
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	public static boolean FORCE_LOAD_BUNDLE = true;
	private static final String BUNDLE_NAME = "localization.wizard"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();
	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public static String getString(String key, String defaultValue) {
		try {
			// Force update
			ResourceBundle bundle = /*Beans.isDesignTime()*/FORCE_LOAD_BUNDLE ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}
	
	public static ArrayList<String> getString(Collection<String> l) {
		ArrayList<String> result = new ArrayList<>(l.size());
		for (String v : l) {
			result.add(eu.citadel.converter.localization.Messages.getString(v));
		}
		return result;
	}
	
	public static ArrayList<Pair<String, String>> getStringMap(Map<String, String> mapKeyAndTranslationKey) {
		ArrayList<Pair<String, String>> result = new ArrayList<>(mapKeyAndTranslationKey.size());
		for (Entry<String, String> v : mapKeyAndTranslationKey.entrySet()) {
			result.add(new Pair<String,String>(eu.citadel.converter.localization.Messages.getString(v.getValue()), v.getKey()));
		}
		return result;
	}
	
	public static String untranslate(String match, java.util.List<Pair<String, String>> categoriesMap) {
		for (Pair<String, String> pair : categoriesMap) {			
			String translated = pair.getLeft();
			if(translated.equals(match)) {
				return pair.getRight();
			}
		}
		return null;
	}
}
