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

class FilterByIngredientFragment : Fragment() {

    private lateinit var editIngredient: EditText
    private lateinit var btnSearch: Button
    private lateinit var llResultsContainer: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_filter_by_ingredient, container, false)

        editIngredient = view.findViewById(R.id.edit_ingredient)
        btnSearch = view.findViewById(R.id.btn_search_ingredient)
        llResultsContainer = view.findViewById(R.id.ll_results_container)

        btnSearch.setOnClickListener {
            val ingredient = editIngredient.text.toString()
            if (ingredient.isNotBlank()) {
                hideKeyboard()
                buscarReceitasPorIngrediente(ingredient)
            }
        }

        return view
    }

    private fun buscarReceitasPorIngrediente(ingredient: String) {
        RetrofitInstance.api.filterByIngredient(ingredient)
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                    val meals = response.body()?.meals
                    llResultsContainer.removeAllViews()

                    if (meals.isNullOrEmpty()) {
                        val tvEmpty = TextView(requireContext())
                        tvEmpty.text = "Nada encontrado."
                        tvEmpty.setPadding(16,16,16,16)
                        llResultsContainer.addView(tvEmpty)
                    } else {
                        meals.forEach { meal ->
                            val itemView = layoutInflater.inflate(R.layout.item_meal_with_image, llResultsContainer, false)

                            val ivMeal = itemView.findViewById<ImageView>(R.id.iv_meal_image)
                            val tvMealName = itemView.findViewById<TextView>(R.id.tv_meal_name)

                            tvMealName.text = meal.strMeal
                            Glide.with(this@FilterByIngredientFragment)
                                .load(meal.strMealThumb)
                                .placeholder(R.drawable.ic_food_placeholder)
                                .into(ivMeal)

                            llResultsContainer.addView(itemView)
                        }
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    llResultsContainer.removeAllViews()
                    val tvError = TextView(requireContext())
                    tvError.text = "Erro: ${t.message}"
                    tvError.setPadding(16,16,16,16)
                    llResultsContainer.addView(tvError)
                }
            })
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus ?: View(requireContext())
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
