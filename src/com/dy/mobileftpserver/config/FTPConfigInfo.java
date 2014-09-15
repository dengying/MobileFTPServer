package com.dy.mobileftpserver.config;

public class FTPConfigInfo {
	//管理员
	private String adminUserpassword="";
	private String adminHomedirectory="";
	private boolean adminEnableflag=true;
	private boolean adminWritepermission=true;
	private int adminMaxloginnumber=0;
	private int adminMaxloginperip=0;
	private int adminIdletime=0;
	private int adminUploadrate=0;
	private int adminDownloadrate=0;
	//匿名
	private String anonymousUserpassword="";
	private String anonymousHomedirectory="";
	private boolean anonymousEnableflag=true;
	private boolean anonymousWritepermission=false;
	private int anonymousMaxloginnumber=20;
	private int anonymousMaxloginperip=2;
	private int anonymousIdletime=300;
	private int anonymousUploadrate=4800;
	private int anonymousDownloadrate=4800;
	
	public String getAdminUserpassword() {
		return adminUserpassword;
	}
	public void setAdminUserpassword(String adminUserpassword) {
		this.adminUserpassword = adminUserpassword;
	}
	public String getAdminHomedirectory() {
		return adminHomedirectory;
	}
	public void setAdminHomedirectory(String adminHomedirectory) {
		this.adminHomedirectory = adminHomedirectory;
	}
	public boolean isAdminEnableflag() {
		return adminEnableflag;
	}
	public void setAdminEnableflag(boolean adminEnableflag) {
		this.adminEnableflag = adminEnableflag;
	}
	public boolean isAdminWritepermission() {
		return adminWritepermission;
	}
	public void setAdminWritepermission(boolean adminWritepermission) {
		this.adminWritepermission = adminWritepermission;
	}
	public int getAdminMaxloginnumber() {
		return adminMaxloginnumber;
	}
	public void setAdminMaxloginnumber(int adminMaxloginnumber) {
		this.adminMaxloginnumber = adminMaxloginnumber;
	}
	public int getAdminMaxloginperip() {
		return adminMaxloginperip;
	}
	public void setAdminMaxloginperip(int adminMaxloginperip) {
		this.adminMaxloginperip = adminMaxloginperip;
	}
	public int getAdminIdletime() {
		return adminIdletime;
	}
	public void setAdminIdletime(int adminIdletime) {
		this.adminIdletime = adminIdletime;
	}
	public int getAdminUploadrate() {
		return adminUploadrate;
	}
	public void setAdminUploadrate(int adminUploadrate) {
		this.adminUploadrate = adminUploadrate;
	}
	public int getAdminDownloadrate() {
		return adminDownloadrate;
	}
	public void setAdminDownloadrate(int adminDownloadrate) {
		this.adminDownloadrate = adminDownloadrate;
	}
	public String getAnonymousUserpassword() {
		return anonymousUserpassword;
	}
	public void setAnonymousUserpassword(String anonymousUserpassword) {
		this.anonymousUserpassword = anonymousUserpassword;
	}
	public String getAnonymousHomedirectory() {
		return anonymousHomedirectory;
	}
	public void setAnonymousHomedirectory(String anonymousHomedirectory) {
		this.anonymousHomedirectory = anonymousHomedirectory;
	}
	public boolean isAnonymousEnableflag() {
		return anonymousEnableflag;
	}
	public void setAnonymousEnableflag(boolean anonymousEnableflag) {
		this.anonymousEnableflag = anonymousEnableflag;
	}
	public boolean isAnonymousWritepermission() {
		return anonymousWritepermission;
	}
	public void setAnonymousWritepermission(boolean anonymousWritepermission) {
		this.anonymousWritepermission = anonymousWritepermission;
	}
	public int getAnonymousMaxloginnumber() {
		return anonymousMaxloginnumber;
	}
	public void setAnonymousMaxloginnumber(int anonymousMaxloginnumber) {
		this.anonymousMaxloginnumber = anonymousMaxloginnumber;
	}
	public int getAnonymousMaxloginperip() {
		return anonymousMaxloginperip;
	}
	public void setAnonymousMaxloginperip(int anonymousMaxloginperip) {
		this.anonymousMaxloginperip = anonymousMaxloginperip;
	}
	public int getAnonymousIdletime() {
		return anonymousIdletime;
	}
	public void setAnonymousIdletime(int anonymousIdletime) {
		this.anonymousIdletime = anonymousIdletime;
	}
	public int getAnonymousUploadrate() {
		return anonymousUploadrate;
	}
	public void setAnonymousUploadrate(int anonymousUploadrate) {
		this.anonymousUploadrate = anonymousUploadrate;
	}
	public int getAnonymousDownloadrate() {
		return anonymousDownloadrate;
	}
	public void setAnonymousDownloadrate(int anonymousDownloadrate) {
		this.anonymousDownloadrate = anonymousDownloadrate;
	}
	
}
