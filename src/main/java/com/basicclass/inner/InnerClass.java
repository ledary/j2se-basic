package com.basicclass.inner;

/**
 * @author WGP
 * @descriptioncrude-trade
 * @date 2018/5/24
 **/
 public  class InnerClass {




    static public class InnerStatic{
        private  Integer a = 1;
        private  Integer b = 2;

        public   Integer getA() {
            return a;
        }

        public  void setA(Integer a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public String getStr(){
            return "dfdf";
        }
    }

    class InnerUnStatic{
        public String innerMessage = "innerMessage";
    }
}
