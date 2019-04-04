package com.pndoo.grown123_new.dto.base;

/**
 * Created by BAO on 2017-07-25.
 */

public class LoginBean {

    /**
     * code : SUCCESS
     * codeInfo : 用户登陆成功!
     * data : {"userId":"36","userName":"123645543","userEmail":"123@qq.com","userDetail":{"address1":"ds","birthday":{"date":3,"day":5,"hours":0,"minutes":0,"month":1,"seconds":0,"time":602438400000,"timezoneOffset":-480,"year":89},"birthdayShow":"1989-02-03","parents":"s","id":5,"kindergarten":"aaa","level":1,"sex":1,"userId":36,"subscibed":1},"userStatus":0,"userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}
     */

    private String code;
    private String codeInfo;
    private Data data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        /**
         * userId : 36
         * userName : 123645543
         * userEmail : 123@qq.com
         * userDetail : {"address1":"ds","birthday":{"date":3,"day":5,"hours":0,"minutes":0,"month":1,"seconds":0,"time":602438400000,"timezoneOffset":-480,"year":89},"birthdayShow":"1989-02-03","parents":"s","id":5,"kindergarten":"aaa","level":1,"sex":1,"userId":36,"subscibed":1}
         * userStatus : 0
         * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
         */

        private String userId;
        private String userName;
        private String userEmail;
        private UserDetail userDetail;
        private int userStatus;
        private String userPortraits;
        private int isHaveGroup;

        public int getIsHaveGroup() {
            return isHaveGroup;
        }

        public void setIsHaveGroup(int isHaveGroup) {
            this.isHaveGroup = isHaveGroup;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public void setUserPortraits(String userPortraits) {
            this.userPortraits = userPortraits;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public String getUserPortraits() {
            return userPortraits;
        }

        public static class UserDetail {
            /**
             * address1 : ds
             * birthday : {"date":3,"day":5,"hours":0,"minutes":0,"month":1,"seconds":0,"time":602438400000,"timezoneOffset":-480,"year":89}
             * birthdayShow : 1989-02-03
             * parents : s
             * id : 5
             * kindergarten : aaa
             * level : 1
             * sex : 1
             * userId : 36
             * subscibed : 1
             */

            private String address1;
            private String birthdayShow;
            private String parents;
            private String kindergarten;
            private String level;
            private int sex;
            private int subscibed;
            private String realName;
            private String surname;

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getSurname() {
                return surname;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public void setAddress1(String address1) {
                this.address1 = address1;
            }


            public void setBirthdayShow(String birthdayShow) {
                this.birthdayShow = birthdayShow;
            }

            public void setParents(String parents) {
                this.parents = parents;
            }


            public void setKindergarten(String kindergarten) {
                this.kindergarten = kindergarten;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }


            public void setSubscibed(int subscibed) {
                this.subscibed = subscibed;
            }

            public String getAddress1() {
                return address1;
            }


            public String getBirthdayShow() {
                return birthdayShow;
            }

            public String getParents() {
                return parents;
            }


            public String getKindergarten() {
                return kindergarten;
            }

            public String getLevel() {
                return level;
            }

            public int getSex() {
                return sex;
            }


            public int getSubscibed() {
                return subscibed;
            }
        }
    }
}
