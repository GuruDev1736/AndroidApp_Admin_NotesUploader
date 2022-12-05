package com.guruprasad.notesuplaoder;

public class usermodel {

        String full_name , email, password , phone_no;
        String profile_pic ;


        public usermodel(String full_name, String email, String password, String phone_no) {
                this.full_name = full_name;
                this.email = email;
                this.password = password;
                this.phone_no = phone_no;

        }

        public usermodel(String profile_pic) {
                this.profile_pic = profile_pic;
        }

        public String getProfile_pic() {
                return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
                this.profile_pic = profile_pic;
        }

        public usermodel() {
        }

        public String getFull_name() {
                return full_name;
        }

        public void setFull_name(String full_name) {
                this.full_name = full_name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getPhone_no() {
                return phone_no;
        }

        public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
        }


}
