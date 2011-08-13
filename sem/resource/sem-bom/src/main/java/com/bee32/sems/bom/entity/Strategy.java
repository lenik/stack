package com.bee32.sems.bom.entity;

/**
 * 原材料价格获取策略对应的枚举类
 */
public enum Strategy {
       NEWESTPRICE(1),  //最新价格
       AVGPRICE(2); //平均价格

       private int value;
       private String text;

       private Strategy(int param) {
           this.value  = param;
           switch (param) {
               case 1:
                   this.text = "最新价格";
                   break;
               case 2:
                   this.text = "平均价格";
                   break;
           }
       }

       public int value() {
           return this.value;
       }

       public String text() {
           return this.text;
       }

       public static Strategy parseInt(int param) {
           switch(param) {
               case 1:
                   return Strategy.NEWESTPRICE;
               case 2:
                   return Strategy.AVGPRICE;
           }

           return NEWESTPRICE;
       }
   }
