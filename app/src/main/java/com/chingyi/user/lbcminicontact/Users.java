package com.chingyi.user.lbcminicontact;

public class Users {
    String name,image,phone,phone1;

    public Users(){

    }
    public Users(String name,String phone ,String image){
        this.name = name;
        this.phone= phone;
        this.image = image;

    }
    public Users(String name, String phone, String image, String phone1){
        this.name = name;
        this.phone= phone;
        this.image = image;
        this.phone1=phone1;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
