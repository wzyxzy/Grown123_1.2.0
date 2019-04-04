package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2017-07-20.
 */

public class ClassDetailBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"groupCode":"169459","groupName":"1","groupCity":"1","groupKindergarten":"1","groupClass":"1","users":[{"isAdmin":1,"userName":"123654321","surname":"ff","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"123654","surname":"ee","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"1232133654","surname":"ddd","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"13341192873","surname":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"1236543","surname":"aa","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]}
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
         * groupCode : 169459
         * groupName : 1
         * groupCity : 1
         * groupKindergarten : 1
         * groupClass : 1
         * users : [{"isAdmin":1,"userName":"123654321","surname":"ff","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"123654","surname":"ee","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"1232133654","surname":"ddd","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"13341192873","surname":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"isAdmin":0,"userName":"1236543","surname":"aa","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]
         */
        private int groupId;
        private String groupCode;
        private String groupName;
        private String groupCity;
        private String groupKindergarten;
        private String groupClass;
        private List<Users> users;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public void setGroupCode(String groupCode) {
            this.groupCode = groupCode;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public void setGroupCity(String groupCity) {
            this.groupCity = groupCity;
        }

        public void setGroupKindergarten(String groupKindergarten) {
            this.groupKindergarten = groupKindergarten;
        }

        public void setGroupClass(String groupClass) {
            this.groupClass = groupClass;
        }

        public void setUsers(List<Users> users) {
            this.users = users;
        }

        public String getGroupCode() {
            return groupCode;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getGroupCity() {
            return groupCity;
        }

        public String getGroupKindergarten() {
            return groupKindergarten;
        }

        public String getGroupClass() {
            return groupClass;
        }

        public List<Users> getUsers() {
            return users;
        }

        public static class Users {
            /**
             * isAdmin : 1
             * userName : 123654321
             * surname : ff
             * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
             */

            private int isAdmin;
            private String userName;
            private String surname;
            private String userPortraits;

            public void setIsAdmin(int isAdmin) {
                this.isAdmin = isAdmin;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public void setUserPortraits(String userPortraits) {
                this.userPortraits = userPortraits;
            }

            public int getIsAdmin() {
                return isAdmin;
            }

            public String getUserName() {
                return userName;
            }

            public String getSurname() {
                return surname;
            }

            public String getUserPortraits() {
                return userPortraits;
            }
        }
    }
}
