package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2016-09-21.
 */
public class SourceBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : [{"code":"fsdf","id":1,"lecturer":"fsda","photourl":"sdfsd","startDate":"2016-09-21-2016-09-21","startTime":"12:20:50-12:20:50","title":"sdfa"},{"code":"fdsaf","id":2,"lecturer":"vxczvxc","photourl":"fsdaf","startDate":"2016-09-21-2016-09-21","startTime":"12:20:50-12:20:50","title":"cxvzvzx"}]
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
         * code : fsdf
         * id : 1
         * lecturer : fsda
         * photourl : sdfsd
         * startDate : 2016-09-21-2016-09-21
         * startTime : 12:20:50-12:20:50
         * title : sdfa
         */

        private String code;
        private int id;
        private String lecturer;
        private String photourl;
        private String startDate;
        private String startTime;
        private String title;
        private int available;//0 没有 1 有

        public int getAvailable() {
            return available;
        }

        public void setAvailable(int available) {
            this.available = available;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLecturer(String lecturer) {
            this.lecturer = lecturer;
        }

        public void setPhotourl(String photourl) {
            this.photourl = photourl;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public int getId() {
            return id;
        }

        public String getLecturer() {
            return lecturer;
        }

        public String getPhotourl() {
            return photourl;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getTitle() {
            return title;
        }
    }
}
