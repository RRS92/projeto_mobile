package com.rebson.projetomobile.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rebson.projetomobile.R
import com.rebson.projetomobile.data.model.MealResponse
import com.rebson.projetomobile.data.repository.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterByLetterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter_by_letter, container, false)

        val editLetter = view.findViewById<EditText>(R.id.edit_letter)
        val btnSearch = view.findViewById<Button>(R.id.btn_search_letter)
        val resultContainer = view.findViewById<LinearLayout>(R.id.ll_result_container)

        btnSearch.setOnClickListener {
            val letter = editLetter.text.toString()
            if (letter.length == 1) {
                hideKeyboard()
                resultContainer.removeAllViews()

                RetrofitInstance.api.searchMealByFirstLetter(letter[0])
                    .enqueue(object : Callback<MealResponse> {
                        override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                            val meals = response.body()?.meals
                            if (!meals.isNullOrEmpty()) {
                                for (meal in meals) {
                                    val itemLayout = LinearLayout(requireContext()).apply {
                                        orientation = LinearLayout.HORIZONTAL
                                        setPadding(16)
                                    }

                                    val imageView = ImageView(requireContext()).apply {
                                        layoutParams = LinearLayout.LayoutParams(200, 200)
                                        scaleType = ImageView.ScaleType.CENTER_CROP
                                    }

                                    val textView = TextView(requireContext()).apply {
                                        layoutParams = LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                        ).apply {
                                            setMargins(24, 0, 0, 0)
                                        }
                                        text = meal.strMeal
                                        textSize = 18f
                                        setTextColor(resources.getColor(android.R.color.black))
                                        setTypeface(null, android.graphics.Typeface.BOLD)
                                    }

                                    Glide.with(requireContext())
                                        .load(meal.strMealThumb)
                                        .placeholder(R.drawable.ic_food_placeholder)
                                        .into(imageView)

                                    itemLayout.addView(imageView)
                                    itemLayout.addView(textView)
                                    resultContainer.addView(itemLayout)
                                }
                            } else {
                                showTextResult(resultContainer, "Nada encontrado.")
                            }
                        }

                        override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                            showTextResult(resultContainer, "Erro: ${t.message}")
                        }
                    })
            } else {
                showTextResult(resultContainer, "Digite apenas uma letra.")
            }
        }

        return view
    }

    private fun showTextResult(container: LinearLayout, message: String) {
        container.removeAllViews()
        val msgText = TextView(requireContext()).apply {
            text = message
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.black))
            setPadding(16)
        }
        container.addView(msgText)
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus ?: View(requireContext())
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
