package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BAO on 2017-07-14.
 */

public class DoneBean implements Serializable{

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : [{"submissionId":6,"userName":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobContent":"66666","addTime":{"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":18,"time":1498026558000,"timezoneOffset":-480,"year":117},"jobGrade":0,"likeCount":3,"commentCount":3,"jobPictures":[{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}],"islike":1,"likeUsers":[{"likeUser":"ddd"},{"likeUser":"ee"},{"likeUser":"ff"}],"jobComments":[{"commentId":4,"commentUser":"ddd","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"44444444444","commentIsDel":0},{"commentId":5,"commentUser":"ee","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"5555555555","commentIsDel":0},{"commentId":6,"commentUser":"ff","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"666666666","commentIsDel":0}]},{"submissionId":3,"userName":"gg","userPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","jobContent":"333333","addTime":{"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":15,"time":1498026555000,"timezoneOffset":-480,"year":117},"jobGrade":1,"likeCount":4,"commentCount":5,"jobPictures":[{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}],"islike":0,"likeUsers":[{"likeUser":"aa"},{"likeUser":"bb"},{"likeUser":"cc"},{"likeUser":"gg"}],"jobComments":[{"commentId":1,"commentUser":"aa","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"1111111111","commentIsDel":0},{"commentId":2,"commentUser":"bb","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"2222222222","commentIsDel":0},{"commentId":3,"commentUser":"cc","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"3333333333","commentIsDel":0},{"commentId":9,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"aaaa","commentIsDel":1},{"commentId":10,"commentUser":"gg","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"bbbbb","commentIsDel":1}]}]
     */

    private String code;
    private String codeInfo;
    private List<Data> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data implements Serializable{
        /**
         * submissionId : 6
         * userName : gg
         * userPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
         * jobContent : 66666
         * addTime : {"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":18,"time":1498026558000,"timezoneOffset":-480,"year":117}
         * jobGrade : 0
         * likeCount : 3
         * commentCount : 3
         * jobPictures : [{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]
         * islike : 1
         * likeUsers : [{"likeUser":"ddd"},{"likeUser":"ee"},{"likeUser":"ff"}]
         * jobComments : [{"commentId":4,"commentUser":"ddd","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"44444444444","commentIsDel":0},{"commentId":5,"commentUser":"ee","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"5555555555","commentIsDel":0},{"commentId":6,"commentUser":"ff","commentPortraits":"Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg","commentContent":"666666666","commentIsDel":0}]
         */

        private int submissionId;
        private String userName;
        private String userPortraits;
        private String jobContent;
        private AddTime addTime;
        private int jobGrade;
        private int likeCount;
        private int commentCount;
        private int islike;
        private List<JobPictures> jobPictures;
        private List<LikeUsers> likeUsers;
        private List<JobComments> jobComments;

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

        public void setAddTime(AddTime addTime) {
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

        public void setJobPictures(List<JobPictures> jobPictures) {
            this.jobPictures = jobPictures;
        }

        public void setLikeUsers(List<LikeUsers> likeUsers) {
            this.likeUsers = likeUsers;
        }

        public void setJobComments(List<JobComments> jobComments) {
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

        public AddTime getAddTime() {
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

        public List<JobPictures> getJobPictures() {
            return jobPictures;
        }

        public List<LikeUsers> getLikeUsers() {
            return likeUsers;
        }

        public List<JobComments> getJobComments() {
            return jobComments;
        }

        public static class AddTime implements Serializable{
            /**
             * date : 21
             * day : 3
             * hours : 14
             * minutes : 29
             * month : 5
             * seconds : 18
             * time : 1498026558000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public void setDate(int date) {
                this.date = date;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getDate() {
                return date;
            }

            public int getDay() {
                return day;
            }

            public int getHours() {
                return hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public int getMonth() {
                return month;
            }

            public int getSeconds() {
                return seconds;
            }

            public long getTime() {
                return time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public int getYear() {
                return year;
            }
        }

        public static class JobPictures implements Serializable{
            /**
             * jobPicture : homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
             */

            private String jobPicture;

            public void setJobPicture(String jobPicture) {
                this.jobPicture = jobPicture;
            }

            public String getJobPicture() {
                return jobPicture;
            }
        }

        public static class LikeUsers implements Serializable{
            /**
             * likeUser : ddd
             */

            private String likeUser;

            public void setLikeUser(String likeUser) {
                this.likeUser = likeUser;
            }

            public String getLikeUser() {
                return likeUser;
            }
        }

        public static class JobComments implements Serializable{
            /**
             * commentId : 4
             * commentUser : ddd
             * commentPortraits : Portraits/32363953-def9-478b-b9c8-09cdde41dc1b.jpg
             * commentContent : 44444444444
             * commentIsDel : 0
             */

            private int commentId;
            private String commentUser;
            private String commentPortraits;
            private String commentContent;
            private int commentIsDel;

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public void setCommentUser(String commentUser) {
                this.commentUser = commentUser;
            }

            public void setCommentPortraits(String commentPortraits) {
                this.commentPortraits = commentPortraits;
            }

            public void setCommentContent(String commentContent) {
                this.commentContent = commentContent;
            }

            public void setCommentIsDel(int commentIsDel) {
                this.commentIsDel = commentIsDel;
            }

            public int getCommentId() {
                return commentId;
            }

            public String getCommentUser() {
                return commentUser;
            }

            public String getCommentPortraits() {
                return commentPortraits;
            }

            public String getCommentContent() {
                return commentContent;
            }

            public int getCommentIsDel() {
                return commentIsDel;
            }
        }
    }
}
