package com.rebson.projetomobile.ui.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rebson.projetomobile.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val textView = view.findViewById<TextView>(R.id.tv_home_intro)

        val introText = "Explore receitas incríveis, filtre por ingredientes fresquinhos, descubra pratos aleatórios deliciosos e transforme sua cozinha com pratos que você vai amar conhecer!"

        // Criar Spannable para colorir e destacar palavras importantes
        val spannable = SpannableString(introText)

        // Exemplo: destaque em vermelho para algumas palavras
        val palavrasDestaque = listOf("receitas incríveis", "ingredientes fresquinhos", "pratos aleatórios deliciosos", "transforme sua cozinha", "amar conhecer")

        palavrasDestaque.forEach { palavra ->
            val start = introText.indexOf(palavra)
            if (start >= 0) {
                val end = start + palavra.length
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#E53935")),  // vermelho iFood
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        textView.text = spannable
        textView.textSize = 22f  // Um pouco maior para leitura
        textView.setLineSpacing(10f, 1.1f)  // Espaçamento agradável
        textView.setPadding(16, 16, 16, 16)

        return view
    }
}
