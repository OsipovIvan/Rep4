package ru.osipov.nmediaapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.osipov.nmediaapp.adpter.PostClickListener
import ru.osipov.nmediaapp.dto.Post
import ru.osipov.nmediaapp.model.PostViewModel

object AndroidUtils{
    fun hideKeyboard(view: View){
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

