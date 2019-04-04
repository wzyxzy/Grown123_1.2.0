package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2017-07-20.
 */

public class JobDetailBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : [{"submissionId":3,"userName":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobContent":"333333","addTime":{"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":15,"time":1498026555000,"timezoneOffset":-480,"year":117},"jobGrade":1,"likeCount":4,"commentCount":5,"jobPictures":[{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}],"islike":0,"likeUsers":[{"likeUser":"aa"},{"likeUser":"bb"},{"likeUser":"cc"},{"likeUser":"gg"}],"jobComments":[{"commentId":1,"commentUser":"aa","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"1111111111","commentIsDel":0},{"commentId":2,"commentUser":"bb","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"2222222222","commentIsDel":0},{"commentId":3,"commentUser":"cc","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"3333333333","commentIsDel":0},{"commentId":9,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"aaaa","commentIsDel":1},{"commentId":10,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"bbbbb","commentIsDel":1}]}]
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
         * submissionId : 3
         * userName : gg
         * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
         * jobContent : 333333
         * addTime : {"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":15,"time":1498026555000,"timezoneOffset":-480,"year":117}
         * jobGrade : 1
         * likeCount : 4
         * commentCount : 5
         * jobPictures : [{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]
         * islike : 0
         * likeUsers : [{"likeUser":"aa"},{"likeUser":"bb"},{"likeUser":"cc"},{"likeUser":"gg"}]
         * jobComments : [{"commentId":1,"commentUser":"aa","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"1111111111","commentIsDel":0},{"commentId":2,"commentUser":"bb","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"2222222222","commentIsDel":0},{"commentId":3,"commentUser":"cc","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"3333333333","commentIsDel":0},{"commentId":9,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"aaaa","commentIsDel":1},{"commentId":10,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"bbbbb","commentIsDel":1}]
         */

        private int submissionId;
        private String userName;
        private String userPortraits;
        private String jobContent;
        private DoneBean.Data.AddTime addTime;
        private int jobGrade;
        private int likeCount;
        private int commentCount;
        private int islike;
        private List<DoneBean.Data.JobPictures> jobPictures;
        private List<DoneBean.Data.LikeUsers> likeUsers;
        private List<DoneBean.Data.JobComments> jobComments;

        public void setSubmissionId(int submissionId) {
            this.submissionId = submissionId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserPortraits(String userPortraits) {
            this.userPortraits = userPortraits;
        }

        public void setJobContent(String jobContent) {
            this.jobContent = jobContent;
        }

        public void setAddTime(DoneBean.Data.AddTime addTime) {
            this.addTime = addTime;
        }

        public void setJobGrade(int jobGrade) {
            this.jobGrade = jobGrade;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setIslike(int islike) {
            this.islike = islike;
        }

        public void setJobPictures(List<DoneBean.Data.JobPictures> jobPictures) {
            this.jobPictures = jobPictures;
        }

        public void setLikeUsers(List<DoneBean.Data.LikeUsers> likeUsers) {
            this.likeUsers = likeUsers;
        }

        public void setJobComments(List<DoneBean.Data.JobComments> jobComments) {
            this.jobComments = jobComments;
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

        public String getJobContent() {
            return jobContent;
        }

        public DoneBean.Data.AddTime getAddTime() {
            return addTime;
        }

        public int getJobGrade() {
            return jobGrade;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public int getIslike() {
            return islike;
        }

        public List<DoneBean.Data.JobPictures> getJobPictures() {
            return jobPictures;
        }

        public List<DoneBean.Data.LikeUsers> getLikeUsers() {
            return likeUsers;
        }

        public List<DoneBean.Data.JobComments> getJobComments() {
            return jobComments;
        }
    }
}
