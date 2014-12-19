package br.com.isoftgoal.util;

import util.convert.I18N;

public class I18nUtil {
	
	public static String get(String chave) {
		return get(chave, null);
	}
	
	public static String get(String chave, String msgPadrao) {
		return I18N.getMessage(chave, msgPadrao);
	}

}
