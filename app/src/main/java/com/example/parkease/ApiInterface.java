package com.example.parkease;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("readcity.php")
    Call<GetStringResponse> getCityData();
    @GET("readbookedparking.php")
    Call<GetBookedParkingResponse> getbookedparking();
    @GET("deletebookedparking.php")
    Call<InsertResponse> truncatebookedparking();
    @GET("readparkinghistory.php")
    Call<GetBookedParkingResponse> readparkinghistory();

    @FormUrlEncoded
    @POST("readarea.php")
    Call<GetAreaResponse> getAreaData(@Field("city") String city);
    @FormUrlEncoded
    @POST("cancelbooking.php")
    Call<InsertResponse> updatedata(@Field("time") String time,@Field("vehicle_number") String vehicle_number);

    @FormUrlEncoded
    @POST("readavailableparking.php")
    Call<GetAvailableParkingResponse> getAvailableParking(@Field("city") String city,@Field("area") String area);
    @FormUrlEncoded
    @POST("readavailablespot.php")
    Call<GetAvailableSpotResponse> getAvailableSpots(@Field("parking_name") String parking_name);
    @FormUrlEncoded
    @POST("deleteavailableslot.php")
    Call<InsertResponse> deleteavailableslot(@Field("available_spot") String available_spot);

    @FormUrlEncoded
    @POST("writebookedparking.php")
    Call<InsertResponse> putBookedParking(@Field("vehicle_number") String vehicle_number,@Field("phone") String phone,@Field("time") String time,@Field("vehicle_type") String vehicle_type,@Field("parking_name") String parking_name,@Field("slot") String slot,@Field("amount") String amount);

}
