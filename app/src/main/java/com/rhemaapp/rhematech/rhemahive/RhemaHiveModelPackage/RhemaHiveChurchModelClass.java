package com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage;

public class RhemaHiveChurchModelClass {
    public String church_pix;
    public String user_church;
    public String church_address;
    public String church_phone;
    public String church_leaders_name;
    public String church_email;
    public String church_reg_stat;
    private final int NO_IMG_PROV = -1;
    private int imgIcon = NO_IMG_PROV;
    public String church_description;
    public String user_uid;
    public String user_type;


    /**
     * this constructor shows the church details excluding members_count
     * @param user_church
     * @param church_address
     * @param church_phone
     * @param church_leaders_name
     * @param church_email
     * @param church_reg_stat
     * @param church_pix
     * @param user_uid
     */
    public RhemaHiveChurchModelClass(String user_church, String church_address, String church_phone, String church_leaders_name, String church_email, String church_reg_stat, String church_pix, String church_description,String user_uid,String user_type) {
        this.user_church = user_church;
        this.church_address = church_address;
        this.church_phone = church_phone;
        this.church_leaders_name = church_leaders_name;
        this.church_email = church_email;
        this.church_reg_stat = church_reg_stat;
        this.church_pix = church_pix;
        this.church_description = church_description;
        this.user_uid = user_uid;
        this.user_type = user_type;
    }


    /**
     * this is an empty constructor for instantiation
     */
    public RhemaHiveChurchModelClass() {
    }

    /**
     * this method gets church pix
     * @return
     */
    public String getChurch_pix() {
        return church_pix;
    }

    /**
     * this method sets church pix
     * @param church_pix
     */
    public void setChurch_pix(String church_pix) {
        this.church_pix = church_pix;
    }

    /**
     * this method get church name
     * @return
     */
    public String getChurch_name() {
        return user_church;
    }

    /**
     * this method sets church name
     * @param user_church
     */
    public void setChurch_name(String user_church) {
        this.user_church = user_church;
    }

    /**
     * this method gets church address
     * @return
     */
    public String getChurch_address() {
        return church_address;
    }

    /**
     * this method sets church address
     * @param church_address
     */
    public void setChurch_address(String church_address) {
        this.church_address = church_address;
    }

    /**
     * this method gets church phone no
     * @return
     */
    public String getChurch_phone() {
        return church_phone;
    }

    /**
     * this method sets church phone
     * @param church_phone
     */
    public void setChurch_phone(String church_phone) {
        this.church_phone = church_phone;
    }

    /**
     * this method gets church leader name
     * @return
     */
    public String getChurch_leaders_name() {
        return church_leaders_name;
    }

    /**
     * this method sets church leaders name
     * @param church_leaders_name
     */
    public void setChurch_leaders_name(String church_leaders_name) {
        this.church_leaders_name = church_leaders_name;
    }

    /**
     * this method gets church email
     * @return
     */
    public String getChurch_email() {
        return church_email;
    }

    /**
     * this method sets church email
     * @param church_email
     */
    public void setChurch_email(String church_email) {
        this.church_email = church_email;
    }

    /**
     * this method gets registration status
     * @return
     */
    public String getChurch_reg_stat() {
        return church_reg_stat;
    }

    /**
     * this method sets registration status
     * @param church_reg_stat
     */
    public void setChurch_reg_stat(String church_reg_stat) {
        this.church_reg_stat = church_reg_stat;
    }





    /**
     * this checks if an image was passed which returns false if none was passed
     * otherwise true
     * @return
     */
    public boolean hasImage(){
        return imgIcon != NO_IMG_PROV;
    }

    /**
     * this method gets church description
     * @return
     */
    public String getChurch_description() {
        return church_description;
    }

    /**
     * This method is used to set church description
     * @param church_description
     */
    public void setChurch_description(String church_description){
        this.church_description = church_description;
    }
}
