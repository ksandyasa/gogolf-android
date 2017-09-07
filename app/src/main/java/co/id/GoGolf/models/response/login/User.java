package co.id.GoGolf.models.response.login;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class User {
    private String uid;

    private String ldate;

    private String lname;

    private String phone;

    private String status;

    private String country_id;

    private String device_id;

    private String type;

    private String country_name;

    private String rdate;

    private String point;

    private String email;

    private String address;

    private String verified;

    private String birthdate;

    private String gender;

    private String udate;

    private String fname;

    private String lang;

    public String getUid ()
    {
        return uid;
    }

    public void setUid (String uid)
    {
        this.uid = uid;
    }

    public String getLdate ()
    {
        return ldate;
    }

    public void setLdate (String ldate)
    {
        this.ldate = ldate;
    }

    public String getLname ()
    {
        return lname;
    }

    public void setLname (String lname)
    {
        this.lname = lname;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCountry_id ()
    {
        return country_id;
    }

    public void setCountry_id (String country_id)
    {
        this.country_id = country_id;
    }

    public String getDevice_id ()
    {
        return device_id;
    }

    public void setDevice_id (String device_id)
    {
        this.device_id = device_id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCountry_name ()
    {
        return country_name;
    }

    public void setCountry_name (String country_name)
    {
        this.country_name = country_name;
    }

    public String getRdate ()
    {
        return rdate;
    }

    public void setRdate (String rdate)
    {
        this.rdate = rdate;
    }

    public String getPoint ()
    {
        return point;
    }

    public void setPoint (String point)
    {
        this.point = point;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getVerified ()
    {
        return verified;
    }

    public void setVerified (String verified)
    {
        this.verified = verified;
    }

    public String getBirthdate ()
    {
        return birthdate;
    }

    public void setBirthdate (String birthdate)
    {
        this.birthdate = birthdate;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getUdate ()
    {
        return udate;
    }

    public void setUdate (String udate)
    {
        this.udate = udate;
    }

    public String getFname ()
    {
        return fname;
    }

    public void setFname (String fname)
    {
        this.fname = fname;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [uid = "+uid+", ldate = "+ldate+", lname = "+lname+", phone = "+phone+", status = "+status+", country_id = "+country_id+", device_id = "+device_id+", type = "+type+", country_name = "+country_name+", rdate = "+rdate+", point = "+point+", email = "+email+", address = "+address+", verified = "+verified+", birthdate = "+birthdate+", gender = "+gender+", udate = "+udate+", fname = "+fname+", lang = "+lang+"]";
    }
}
