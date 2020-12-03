package com.example.frontend.api;

import com.example.frontend.model.Category;
import com.example.frontend.model.CategoryRequest;
import com.example.frontend.model.Subcategory;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface SubcategoryApi {
    @Headers({"Accept: application/json"})

    @GET("subcategory/")
    Call<Subcategory> getSubcategory(@Query("subcategoryId") int subcategoryId);

    @GET("subcategory/subcategories/")
    Call<ArrayList<Subcategory>> getSubcategoryList(@Query("categoryId") int categoryId);

    @PUT("subcategory")
    Call<Subcategory> editSubcategory(@Body Subcategory subcategory);

    @PUT("subcategory/delete")
    Call<Subcategory> deleteSubcategory(@Body Subcategory subcategory);



}