package com.rhemaapp.rhematech.rhemahive.RhemaHiveModelPackage;

public class RhemaHiveUserModelClass {

    /**
     * this is the declaration of all the variables for accessing the user details
     */
    public String user_fName;
    public String user_lName;
    public String user_gender;
    public String user_type;
    public String user_dob;
    public String user_regStat;
    public String user_pix;
    public String user_email;
    public String user_phone;
    public String user_about;
    public String user_church;
    public String user_branch;
    public String user_address;
    public String user_city;
    public String user_country;
    public String user_postal_code;
    private final int NO_IMG_PROV = -1;
    private int imgIcon = NO_IMG_PROV;
    public String user_uid;
    public String user_full_name;

    /**
     * this is the constructor that doesnt have user_postal_code
     * @param user_fName
     * @param user_lName
     * @param user_gender
     * @param user_type
     * @param user_dob
     * @param user_regStat
     * @param user_pix
     * @param user_email
     * @param user_phone
     * @param user_about
     * @param user_church
     * @param user_branch
     * @param user_address
     * @param user_city
     * @param user_country
     * @param uid
     *
     */
    public RhemaHiveUserModelClass(String user_fName, String user_lName, String user_gender, String user_type, String user_dob, String user_regStat, String user_pix, String user_email, String user_phone, String user_about, String user_church, String user_branch, String user_address, String user_city, String user_country,String uid) {
        this.user_fName = user_fName;
        this.user_lName = user_lName;
        this.user_gender = user_gender;
        this.user_type = user_type;
        this.user_dob = user_dob;
        this.user_regStat = user_regStat;
        this.user_pix = user_pix;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_about = user_about;
        this.user_church = user_church;
        this.user_branch = user_branch;
        this.user_address = user_address;
        this.user_city = user_city;
        this.user_country = user_country;
        this.user_uid = uid;
    }


    public String getUser_uid() {
        return user_uid;
    }

    public String getFullName(String fName, String lName){
        user_full_name = fName + "" + lName;
        return user_full_name;
    }

    /**
     * This is the complete constructor with complete user details
     * @param user_fName
     * @param user_lName
     * @param user_gender
     * @param user_type
     * @param user_dob
     * @param user_regStat
     * @param user_pix
     * @param user_email
     * @param user_phone
     * @param user_about
     * @param user_church
     * @param user_branch
     * @param user_address
     * @param user_city
     * @param user_country
     * @param user_postal_code
     * @param userId
     */
    public RhemaHiveUserModelClass(String user_fName, String user_lName, String user_gender, String user_type, String user_dob, String user_regStat, String user_pix, String user_email, String user_phone, String user_about, String user_church, String user_branch, String user_address, String user_city, String user_country, String user_postal_code, String userId) {
        this.user_fName = user_fName;
        this.user_lName = user_lName;
        this.user_gender = user_gender;
        this.user_type = user_type;
        this.user_dob = user_dob;
        this.user_regStat = user_regStat;
        this.user_pix = user_pix;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_about = user_about;
        this.user_church = user_church;
        this.user_branch = user_branch;
        this.user_address = user_address;
        this.user_city = user_city;
        this.user_country = user_country;
        this.user_postal_code = user_postal_code;
        this.user_uid = userId;
    }


    public RhemaHiveUserModelClass(String user_fName, String user_pix, String user_about, String user_uid) {
        this.user_fName = user_fName;
        this.user_pix = user_pix;
        this.user_about = user_about;
        this.user_uid = user_uid;
    }

    /**
     * this is an empty constructor
     */
    public RhemaHiveUserModelClass() {
    }

    /**
     * this is used to return users first name
     * @return
     */
    public String getUser_fName() {
        return user_fName;
    }

    /**
     * This is used to set the users first name
     * @param user_fName
     */
    public void setUser_fName(String user_fName) {
        this.user_fName = user_fName;
    }


    /**
     * this is used to get the users last name
     * @return
     */
    public String getUser_lName() {
        return user_lName;
    }

    /**
     * this is used to set the users last name
     * @param user_lName
     */
    public void setUser_lName(String user_lName) {
        this.user_lName = user_lName;
    }

    /**
     * this is used to get users gender
     * @return
     */
    public String getUser_gender() {
        return user_gender;
    }

    /**
     * this is used to set users gender
     * @param user_gender
     */
    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    /**
     * this is used to get user type
     * @return
     */
    public String getUser_type() {
        return user_type;
    }

    /**
     * this is used to set user type
     * @param user_type
     */
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    /**
     *This is used to get user dob
     * @return
     */
    public String getUser_dob() {
        return user_dob;
    }

    /**
     * this is used to set user dob
     * @param user_dob
     */
    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    /**
     * this is used to get user reg status
     * @return
     */
    public String getUser_regStat() {
        return user_regStat;
    }

    /**
     * this is used to set user reg status
     * @param user_regStat
     */
    public void setUser_regStat(String user_regStat) {
        this.user_regStat = user_regStat;
    }

    /**
     * this is used to get user pix link
     * @return
     */
    public String getUser_pix() {
        return user_pix;
    }

    /**
     * this is used to set user pix
     * @param user_pix
     */
    public void setUser_pix(String user_pix) {
        this.user_pix = user_pix;
    }

    /**
     * this is used to get user email
     * @return
     */
    public String getUser_email() {
        return user_email;
    }

    /**
     * this is used to set user email
     * @param user_email
     */
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    /**
     * this is used to get user phone
     * @return
     */
    public String getUser_phone() {
        return user_phone;
    }

    /**
     * this is used to set user phone
     * @param user_phone
     */
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    /**
     * this is used to get users about
     * @return
     */
    public String getUser_about() {
        return user_about;
    }

    /**
     * This is used to set about the user
     * @param user_about
     */
    public void setUser_about(String user_about) {
        this.user_about = user_about;
    }

    /**
     * this is used to get user church
     * @return
     */
    public String getUser_church() {
        return user_church;
    }

    /**
     * this is used to set users church
     * @param user_church
     */
    public void setUser_church(String user_church) {
        this.user_church = user_church;
    }

    /**
     * this is used to get users branch
     * @return
     */
    public String getUser_branch() {
        return user_branch;
    }

    /**
     * this is used to set users branch
     * @param user_branch
     */
    public void setUser_branch(String user_branch) {
        this.user_branch = user_branch;
    }

    /**
     * this is used to get users address
     * @return
     */
    public String getUser_address() {
        return user_address;
    }

    /**
     * this is used to set users address
     * @param user_address
     */
    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    /**
     * this is used to get users city
     * @return
     */
    public String getUser_city() {
        return user_city;
    }

    /**
     * this is used to set users city
     * @param user_city
     */
    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    /**
     * this is used to get users country
     * @return
     */
    public String getUser_country() {
        return user_country;
    }

    /**
     * this is used to set users country
     * @param user_country
     */
    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    /**
     * this is used to get users postal code
     * @return
     */
    public String getUser_postal_code() {
        return user_postal_code;
    }

    /**
     * this is used to set users postal code
     * @param user_postal_code
     */
    public void setUser_postal_code(String user_postal_code) {
        this.user_postal_code = user_postal_code;
    }


    /**
     * this checks if an image was passed which returns false if none was passed
     * otherwise true
     * @return
     */
    public boolean hasImage(){
        return imgIcon != NO_IMG_PROV;
    }


}
