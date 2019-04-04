package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2017-07-12.
 */

public class RateBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"doneUser":[{"submissionId":18,"userName":"ddd","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":1},{"submissionId":17,"userName":"aa","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":1},{"submissionId":5,"userName":"cc","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":0},{"submissionId":6,"userName":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":0}],"undoneUser":[{"userName":"ee","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"userName":"bb","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]}
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
         * doneUser : [{"submissionId":18,"userName":"ddd","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":1},{"submissionId":17,"userName":"aa","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":1},{"submissionId":5,"userName":"cc","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":0},{"submissionId":6,"userName":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobGrade":0}]
         * undoneUser : [{"userName":"ee","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"userName":"bb","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]
         */

        private List<DoneUser> doneUser;
        private List<UndoneUser> undoneUser;

        public void setDoneUser(List<DoneUser> doneUser) {
            this.doneUser = doneUser;
        }

        public void setUndoneUser(List<UndoneUser> undoneUser) {
            this.undoneUser = undoneUser;
        }

        public List<DoneUser> getDoneUser() {
            return doneUser;
        }

        public List<UndoneUser> getUndoneUser() {
            return undoneUser;
        }

        public static class DoneUser {
            /**
             * submissionId : 18
             * userName : ddd
             * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
             * jobGrade : 1
             */

            private int submissionId;
            private String userName;
            private String userPortraits;
            private int jobGrade;

            public DoneUser(String userName, String userPortraits, int jobGrade) {
                this.userName = userName;
                this.userPortraits = userPortraits;
                this.jobGrade = jobGrade;
            }

            public void setSubmissionId(int submissionId) {
                this.submissionId = submissionId;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public void setUserPortraits(String userPortraits) {
                this.userPortraits = userPortraits;
            }

            public void setJobGrade(int jobGrade) {
                this.jobGrade = jobGrade;
            }

            public int getSubmissionId() {
                return submissionId;
            }

            public String getUserName() {
                return userName;
            }

            public String getUserPortraits() {
                return userPortraits;
            }

            public int getJobGrade() {
                return jobGrade;
            }
        }

        public static class UndoneUser {
            /**
             * userName : ee
             * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
             */

            private String userName;
            private String userPortraits;

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public void setUserPortraits(String userPortraits) {
                this.userPortraits = userPortraits;
            }

            public String getUserName() {
                return userName;
            }

            public String getUserPortraits() {
                return userPortraits;
            }
        }
    }
}
