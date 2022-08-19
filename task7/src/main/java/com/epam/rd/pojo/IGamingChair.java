package com.epam.rd.pojo;

public interface IGamingChair {

    String getName();
    void setName(String name);
    double getPrice();
    void setPrice(double price);
    int getId();
    void setId(int id);

    int getMaxWeight();
    void setMaxWeight(int maxWeight);

    boolean isArms();
    void setArms(boolean arms);
    boolean isHeadrest();
    void setHeadrest(boolean headrest);

}
