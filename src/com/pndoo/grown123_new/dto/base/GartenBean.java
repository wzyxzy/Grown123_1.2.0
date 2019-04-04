package com.pndoo.grown123_new.dto.base;

import java.util.List;

/**
 * Created by BAO on 2016-09-08.
 */
public class GartenBean {


    /**
     * code : SUCCESS
     * codeInfo : 操作成功!
     * data : {"systemCodes":[{"code":"101010200","id":20,"name":"海淀]幼儿园1","pid":0},{"code":"101010200","id":28,"name":"海淀]幼儿园2","pid":0}]}
     */

    private String code;
    private String codeInfo;
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * code : 101010200
         * id : 20
         * name : 海淀]幼儿园1
         * pid : 0
         */

        private List<SystemCodes> systemCodes;

        public List<SystemCodes> getSystemCodes() {
            return systemCodes;
        }

        public void setSystemCodes(List<SystemCodes> systemCodes) {
            this.systemCodes = systemCodes;
        }

        public static class SystemCodes {
            private String code;
            private int id;
            private String name;
            private int pid;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }
        }
    }
}
