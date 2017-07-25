package com.qq.administrator.myrefreshdemo.diffutils;

/**
 * Created by 崔琦 on 2017/7/25 0025.
 * Describe : .....
 */
public class TestBean implements Cloneable{
    public String name;
    public String love;
    public String desc;
    public int pic1;
    public int pic2;
    public int pic3;
    public String attention;

    public TestBean(String name, String love, String desc, int pic1, int pic2, int pic3, String attention) {
        this.name = name;
        this.love = love;
        this.desc = desc;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.attention = attention;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPic1() {
        return pic1;
    }

    public void setPic1(int pic1) {
        this.pic1 = pic1;
    }

    public int getPic2() {
        return pic2;
    }

    public void setPic2(int pic2) {
        this.pic2 = pic2;
    }

    public int getPic3() {
        return pic3;
    }

    public void setPic3(int pic3) {
        this.pic3 = pic3;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    //用来测试的数据 实现克隆方法
    @Override
    public TestBean clone() throws CloneNotSupportedException {
        TestBean bean = null;
        try {
            bean = (TestBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
