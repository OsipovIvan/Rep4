package ru.osipov.nmediaapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
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

        var callback = requireActivity().onBackPressedDispatcher.addCallback(getViewLifecycleOwner()) {
            mViewModel.setBuffer(binding.editTextAddPost.text.toString())
            findNavController().popBackStack()
        }

        with(binding.editTextAddPost) {
            requestFocus()

            arguments?.let {
                this.setText(it.getString(CONTENT_POST_KEY))
                mViewModel.setBuffer(null)
                callback.remove()
                callback = requireActivity().onBackPressedDispatcher.addCallback(getViewLifecycleOwner()) {
                    mViewModel.editEmpty()
                    findNavController().popBackStack()
                }
            }

            mViewModel.getBuffer()?.let {
                this.setText(it)
            }
        }


        binding.addPost.setOnClickListener {
            mViewModel.changeContent(binding.editTextAddPost.text.toString())
            mViewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            mViewModel.setBuffer(null)
            findNavController().navigateUp()
        }


        return binding.root
    }
}