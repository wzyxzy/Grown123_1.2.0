package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2017-07-20.
 */

public class JobListBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : [{"submissionId":6,"jobContent":"提交66666","addTime":{"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":18,"time":1498026558000,"timezoneOffset":-480,"year":117},"jobPictures":[{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]},{"submissionId":3,"jobContent":"提交333333","addTime":{"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":15,"time":1498026555000,"timezoneOffset":-480,"year":117},"jobPictures":[{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]}]
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

    public static class Data {
        /**
         * submissionId : 6
         * jobContent : 提交66666
         * addTime : {"date":21,"day":3,"hours":14,"minutes":29,"month":5,"seconds":18,"time":1498026558000,"timezoneOffset":-480,"year":117}
         * jobPictures : [{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"},{"jobPicture":"homework/32363953-def9-478b-b9c8-09cdde41dc1b.jpg"}]
         */

        private int submissionId;
        private String jobContent;
        private AddTime addTime;
        private List<JobPictures> jobPictures;

        public void setSubmissionId(int submissionId) {
            this.submissionId = submissionId;
        }

        public void setJobContent(String jobContent) {
            this.jobContent = jobContent;
        }

        public void setAddTime(AddTime addTime) {
            this.addTime = addTime;
        }

        public void setJobPictures(List<JobPictures> jobPictures) {
            this.jobPictures = jobPictures;
        }

        public int getSubmissionId() {
            return submissionId;
        }

        public String getJobContent() {
            return jobContent;
        }

        public AddTime getAddTime() {
            return addTime;
        }

        public List<JobPictures> getJobPictures() {
            return jobPictures;
        }

        public static class AddTime {
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

        public static class JobPictures {
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
    }
}
