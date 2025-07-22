package com.rebson.projetomobile.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.rebson.projetomobile.data.model.MealResponse

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealResponse>

    @GET("search.php")
    fun searchMeal(@Query("s") name: String): Call<MealResponse>

    @GET("search.php")
    fun searchMealByFirstLetter(@Query("f") letter: Char): Call<MealResponse>

    @GET("filter.php")
    fun filterByIngredient(@Query("i") ingredient: String): Call<MealResponse>
}
