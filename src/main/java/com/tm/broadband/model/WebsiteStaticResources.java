package com.tm.broadband.model;

import java.io.Serializable;

public class WebsiteStaticResources implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * BEGIN TABLE MAPPING PROPERTIES
	 */

	private String logo_path;
	private String facebook_lg_path;
	private String googleplus_lg_path;
	private String twitter_lg_path;
	private String youtube_lg_path;

	/*
	 * END TABLE MAPPING PROPERTIES
	 */

	/*
	 * BEGIN RELATED PROPERTIES
	 */

	/*
	 * END RELATED PROPERTIES
	 */

	public String getLogo_path() {
		return logo_path;
	}
	public void setLogo_path(String logo_path) {
		this.logo_path = logo_path;
	}
	public String getFacebook_lg_path() {
		return facebook_lg_path;
	}
	public void setFacebook_lg_path(String facebook_lg_path) {
		this.facebook_lg_path = facebook_lg_path;
	}
	public String getGoogleplus_lg_path() {
		return googleplus_lg_path;
	}
	public void setGoogleplus_lg_path(String googleplus_lg_path) {
		this.googleplus_lg_path = googleplus_lg_path;
	}
	public String getTwitter_lg_path() {
		return twitter_lg_path;
	}
	public void setTwitter_lg_path(String twitter_lg_path) {
		this.twitter_lg_path = twitter_lg_path;
	}
	public String getYoutube_lg_path() {
		return youtube_lg_path;
	}
	public void setYoutube_lg_path(String youtube_lg_path) {
		this.youtube_lg_path = youtube_lg_path;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
