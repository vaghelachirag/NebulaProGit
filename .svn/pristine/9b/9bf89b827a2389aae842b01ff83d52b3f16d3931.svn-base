package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

public class Walkthrough {
    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String ComPro = "false";
    public static final String ComProBack="false";
    public static final String Project = "false";
    public static final String Project_main = "false";
    public static final String Project_main_Back = "false";
    public static final String Event = "false";
    public static final String EventMain = "false";
    public static final String Video = "false";
    public static final String EDocument = "false";
    public static final String Holiday = "false";
    public static final String CustLogin = "false";
    public static final String SiteProgess = "false";
    public static final String ContactUs = "false";
    public static final String MainWalk="false";


    public Walkthrough(Context context) {
        // TODO Auto-generated constructor stub
        sharedpreferences = context.getSharedPreferences(PrefUtils.prefMywalk, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void setMainWalk(Boolean mainWalk) {
        editor.putBoolean(MainWalk, mainWalk);
        editor.commit();
    }

    public boolean getMainWalk() {
        boolean mainWalk = sharedpreferences.getBoolean(MainWalk, false);
        return mainWalk;
    }

    public void setComPro(Boolean companyProfile) {
        editor.putBoolean(ComPro, companyProfile);
        editor.commit();
    }

    public boolean getComPro() {
        boolean companyProfile = sharedpreferences.getBoolean(ComPro, false);
        return companyProfile;
    }

    public void setComProBack(Boolean comProBack) {
        editor.putBoolean(ComProBack, comProBack);
        editor.commit();
    }

    public boolean getComProBack() {
        boolean comproback = sharedpreferences.getBoolean(ComProBack, false);
        return comproback;
    }

    public void setProject(Boolean project) {
        editor.putBoolean(Project, project);
        editor.commit();
    }

    public Boolean getProject() {
        Boolean companyProfile = sharedpreferences.getBoolean(Project, false);
        return companyProfile;
    }

    public void setProject_main(Boolean project_main) {
        editor.putBoolean(Project_main, project_main);
        editor.commit();
    }

    public Boolean getProject_main() {
        Boolean project_main = sharedpreferences.getBoolean(Project_main, false);
        return project_main;
    }

    public void setProject_main_Back(Boolean project_main_back) {
        editor.putBoolean(Project_main_Back, project_main_back);
        editor.commit();
    }

    public Boolean getProject_main_Back() {
        Boolean project_main_back = sharedpreferences.getBoolean(Project_main_Back, false);
        return project_main_back;
    }

    public void setEvent(Boolean event) {
        editor.putBoolean(Event, event);
        editor.commit();
    }

    public Boolean getEvent() {
        Boolean event = sharedpreferences.getBoolean(Event, false);
        return event;
    }

    public void setEventMain(Boolean eventMain) {
        editor.putBoolean(EventMain, eventMain);
        editor.commit();
    }

    public Boolean getEventMain() {
        Boolean eventmain = sharedpreferences.getBoolean(EventMain, false);
        return eventmain;
    }



    public void setEDocument(Boolean edocument) {
        editor.putBoolean(EDocument, edocument);
        editor.commit();
    }

    public Boolean getEDocument() {
        Boolean edocument = sharedpreferences.getBoolean(EDocument, false);
        return edocument;
    }

    public void setVideo(Boolean video) {
        editor.putBoolean(Video, video);
        editor.commit();
    }

    public Boolean getVideo() {
        Boolean video = sharedpreferences.getBoolean(Video, false);
        return video;
    }

    public void setHoliday(Boolean holiday) {
        editor.putBoolean(Holiday, holiday);
        editor.commit();
    }

    public Boolean getHoliday() {
        Boolean holiday = sharedpreferences.getBoolean(Holiday, false);
        return holiday;
    }

    public void setCustLogin(Boolean custLogin) {
        editor.putBoolean(CustLogin, custLogin);
        editor.commit();
    }

    public Boolean getCustLogin() {
        Boolean custLogin = sharedpreferences.getBoolean(CustLogin, false);
        return custLogin;
    }

    public void setSiteProgess(Boolean siteProgess) {
        editor.putBoolean(SiteProgess, siteProgess);
        editor.commit();
    }

    public Boolean getSiteProgess() {
        Boolean siteProgess = sharedpreferences.getBoolean(SiteProgess, false);
        return siteProgess;
    }


    public void setContactUs(Boolean contactUs) {
        editor.putBoolean(ContactUs, contactUs);
        editor.commit();
    }

    public Boolean getContactUs() {
        Boolean contactUs = sharedpreferences.getBoolean(ContactUs, false);
        return contactUs;
    }


    public void clear(){
        editor.remove(MainWalk).commit();
        editor.remove(ComPro).commit();
        editor.remove(Project).commit();
        editor.remove(Event).commit();
        editor.remove(EventMain).commit();
        editor.remove(Video).commit();
        editor.remove(EDocument).commit();
        editor.remove(Holiday).commit();
        editor.remove(CustLogin).commit();
        editor.remove(SiteProgess).commit();
        editor.remove(ContactUs).commit();
        editor.remove(Project_main).commit();
        editor.remove(ComProBack).commit();
        editor.remove(Project_main_Back).commit();
        editor.commit();
    }
}
