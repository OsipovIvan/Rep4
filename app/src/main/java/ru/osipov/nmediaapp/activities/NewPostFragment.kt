package ru.osipov.nmediaapp.activities

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
import ru.osipov.nmediaapp.utils.CONTENT_POST_KEY

class NewPostFragment : Fragment() {


    private val mViewModel: PostViewModel by viewModels(
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

        with(binding.editTextAddPost) {
            requestFocus()

            arguments?.let {
                this.setText(it.getString(CONTENT_POST_KEY))
            }
        }


        binding.addPost.setOnClickListener {
            mViewModel.changeContent(binding.editTextAddPost.text.toString())
            mViewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }


        return binding.root
    }
}