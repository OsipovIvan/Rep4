package ru.osipov.nmediaapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.osipov.nmediaapp.databinding.FragmentNewPostBinding
import ru.osipov.nmediaapp.model.PostViewModel
import ru.osipov.nmediaapp.utils.AndroidUtils
import ru.osipov.nmediaapp.utils.StringArg

class NewPostFragment : Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        with(binding.editTextAddPost){
            requestFocus()
//            setText(intent?.getStringExtra(android.content.Intent.EXTRA_TEXT))
        }

        binding.addPost.setOnClickListener {
            viewModel.changeContent(binding.editTextAddPost.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        arguments?.textArg
            ?.let(binding.editTextAddPost::setText)


        return binding.root
    }
}