package com.example.aa01_weather_ver02;

public class MyRecyclerViewData {
    private String mv_Time;
    private String mv_City;
    private int mv_Temp;
    private String mv_Con;
    private int mv_TempH;
    private int mv_TempL;

    public MyRecyclerViewData(){
    }

    public MyRecyclerViewData(String Time, String City, int Temp, String Con){
        mv_Time = Time;
        mv_City = City;
        mv_Temp = Temp;
        mv_Con = Con;
    }

    public String getZip(){
        return this.mv_Time;
    }

    public String getCity(){
        return this.mv_City;
    }

    public int getTemp(){
        return this.mv_Temp;
    }
    public int getTempH(){
        return this.mv_TempH;
    }
    public int getTempL(){
        return this.mv_TempL;
    }
    public String getCon(){
        return this.mv_Con;
    }

    public void setCon(String Con){
        mv_Con = Con;
    }

    public void setTime(String Time){
        mv_Time = Time;
    }

    public void setCity(String City){
        mv_City = City;
    }

    public void setTemp(int Temp){
        mv_Temp = Temp;
    }

}