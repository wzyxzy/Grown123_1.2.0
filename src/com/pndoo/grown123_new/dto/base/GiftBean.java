package com.pndoo.grown123_new.dto.base;

/**
 * Created by BAO on 2017-05-03.
 */

public class GiftBean {

    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"presentId":1,"presentName":"啊啊啊"}
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
         * presentId : 1
         * presentName : 啊啊啊
         */

        private int presentId;
        private String presentName;

        public void setPresentId(int presentId) {
            this.presentId = presentId;
        }

        public void setPresentName(String presentName) {
            this.presentName = presentName;
        }

        public int getPresentId() {
            return presentId;
        }

        public String getPresentName() {
            return presentName;
        }
    }
}
