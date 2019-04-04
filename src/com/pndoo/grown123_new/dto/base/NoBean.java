package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class NoBean {


    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : [{"homeworkId":4,"title":"dasdasd11112","introduction":"asdasdas11112","content":"dasda11112","endTime":{"date":6,"day":2,"hours":0,"minutes":0,"month":5,"seconds":0,"time":1496678400000,"timezoneOffset":-480,"year":117},"imagePath":"2016\\12\\27\\channels\\035eb0a1-878b-4c62-8d6b-388acaf2b3cf_iphone.jpg"},{"homeworkId":5,"title":"dasdasd11112","introduction":"asdasdas11112","content":"dasda11112","endTime":{"date":6,"day":2,"hours":0,"minutes":0,"month":5,"seconds":0,"time":1496678400000,"timezoneOffset":-480,"year":117},"imagePath":"2016\\12\\27\\channels\\035eb0a1-878b-4c62-8d6b-388acaf2b3cf_iphone.jpg"}]
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
         * homeworkId : 4
         * title : dasdasd11112
         * introduction : asdasdas11112
         * content : dasda11112
         * endTime : {"date":6,"day":2,"hours":0,"minutes":0,"month":5,"seconds":0,"time":1496678400000,"timezoneOffset":-480,"year":117}
         * imagePath : 2016\12\27\channels\035eb0a1-878b-4c62-8d6b-388acaf2b3cf_iphone.jpg
         */

        private int homeworkId;
        private String title;
        private String introduction;
        private String content;
        private EndTime endTime;
        private String imagePath;

        public void setHomeworkId(int homeworkId) {
            this.homeworkId = homeworkId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setEndTime(EndTime endTime) {
            this.endTime = endTime;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getHomeworkId() {
            return homeworkId;
        }

        public String getTitle() {
            return title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public String getContent() {
            return content;
        }

        public EndTime getEndTime() {
            return endTime;
        }

        public String getImagePath() {
            return imagePath;
        }

        public static class EndTime {
            /**
             * date : 6
             * day : 2
             * hours : 0
             * minutes : 0
             * month : 5
             * seconds : 0
             * time : 1496678400000
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
    }
}
