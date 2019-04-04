package com.pndoo.grown123_new.main;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
	/**
	 * Authority
	 */
	public final static String AUTHORITY = "com.shopin.mobile.SuggestionProvider";
	/**
	 * Mode
	 */
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionProvider() {
		super();
		setupSuggestions(AUTHORITY, MODE);
	}
}