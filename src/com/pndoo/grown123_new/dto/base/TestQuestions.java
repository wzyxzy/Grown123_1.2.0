package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;
import java.util.List;

import com.pndoo.grown123_new.annotations.ComplexSerializableType;

/**
 * Created by ping on 2015-10-20.
 */
public class TestQuestions implements Serializable {


    /**
     * size : 1
     * selectQuesitons : [{"id":"1","name":"选择题","selectOptions":[{"id":"1","name":"sfads","optionCode":"A"},{"id":"2","name":"FSD","optionCode":"B"},{"id":"3","name":"FSDFSDF","optionCode":"C"},{"id":"4","name":"FDSFWERW","optionCode":"D"}]}]
     * usedTime : 2
     */

    private String size;
    private String usedTime;
    private List<SelectQuesitons> selectQuesitons;

    public void setSize(String size) {
        this.size = size;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }
    @ComplexSerializableType(clazz=SelectQuesitons.class)
    public void setSelectQuesitons(List<SelectQuesitons> selectQuesitons) {
        this.selectQuesitons = selectQuesitons;
    }

    public String getSize() {
        return size;
    }

    public String getUsedTime() {
        return usedTime;
    }
    @ComplexSerializableType(clazz=SelectQuesitons.class)
    public List<SelectQuesitons> getSelectQuesitons() {
        return selectQuesitons;
    }

    @Override
	public String toString() {
		return "TestQuestions [size=" + size + ", usedTime=" + usedTime
				+ ", selectQuesitons=" + selectQuesitons + "]";
	}

	public static class SelectQuesitons {
        /**
         * id : 1
         * name : fsfsdfsdfs
         * selectOptions : [{"id":"1","name":"sfads","optionCode":"A"},{"id":"2","name":"FSD","optionCode":"B"},{"id":"3","name":"FSDFSDF","optionCode":"C"},{"id":"4","name":"FDSFWERW","optionCode":"D"}]
         */

        private String id;
        private String name;
        private List<SelectOptions> selectOptions;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
        @ComplexSerializableType(clazz=SelectOptions.class)
        public void setSelectOptions(List<SelectOptions> selectOptions) {
            this.selectOptions = selectOptions;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        @ComplexSerializableType(clazz=SelectOptions.class)
        public List<SelectOptions> getSelectOptions() {
            return selectOptions;
        }

        @Override
		public String toString() {
			return "SelectQuesitons [id=" + id + ", name=" + name
					+ ", selectOptions=" + selectOptions + "]";
		}

		public static class SelectOptions {
            /**
             * id : 1
             * name : sfads
             * optionCode : A
             */

            private String id;
            private String name;
            private String optionCode;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setOptionCode(String optionCode) {
                this.optionCode = optionCode;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getOptionCode() {
                return optionCode;
            }

			@Override
			public String toString() {
				return "SelectOptions [id=" + id + ", name=" + name
						+ ", optionCode=" + optionCode + "]";
			}
            
        }
    }
}

