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
	private String two_dimensional_code_path;
	private String facebook_url;
	private String googleplus_url;
	private String twitter_url;
	private String youtube_url;


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
	public String getFacebook_url() {
		return facebook_url;
	}
	public void setFacebook_url(String facebook_url) {
		this.facebook_url = facebook_url;
	}
	public String getGoogleplus_url() {
		return googleplus_url;
	}
	public void setGoogleplus_url(String googleplus_url) {
		this.googleplus_url = googleplus_url;
	}
	public String getTwitter_url() {
		return twitter_url;
	}
	public void setTwitter_url(String twitter_url) {
		this.twitter_url = twitter_url;
	}
	public String getYoutube_url() {
		return youtube_url;
	}
	public void setYoutube_url(String youtube_url) {
		this.youtube_url = youtube_url;
	}
	public String getTwo_dimensional_code_path() {
		return two_dimensional_code_path;
	}
	public void setTwo_dimensional_code_path(String two_dimensional_code_path) {
		this.two_dimensional_code_path = two_dimensional_code_path;
	}


}
