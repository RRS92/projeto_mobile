package com.rebson.projetomobile.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rebson.projetomobile.R
import com.rebson.projetomobile.data.model.MealResponse
import com.rebson.projetomobile.data.repository.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_random, container, false)

        val btnRandom = view.findViewById<Button>(R.id.btn_get_random)
        val tvResult = view.findViewById<TextView>(R.id.tv_result_random)
        val ivMeal = view.findViewById<ImageView>(R.id.iv_random_meal)

        btnRandom.setOnClickListener {
            hideKeyboard()

            RetrofitInstance.api.getRandomMeal()
                .enqueue(object : Callback<MealResponse> {
                    override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                        val meal = response.body()?.meals?.firstOrNull()
                        if (meal != null) {
                            tvResult.text = meal.strMeal
                            ivMeal.visibility = View.VISIBLE

                            Glide.with(requireContext())
                                .load(meal.strMealThumb)
                                .placeholder(R.drawable.ic_food_placeholder)
                                .into(ivMeal)
                        } else {
                            tvResult.text = "Nada encontrado."
                            ivMeal.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                        tvResult.text = "Erro: ${t.message}"
                        ivMeal.visibility = View.GONE
                    }
                })
        }

        return view
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus ?: View(requireContext())
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
