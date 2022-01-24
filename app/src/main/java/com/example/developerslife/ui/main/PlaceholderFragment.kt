package com.example.developerslife.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.developerslife.R
import com.example.developerslife.databinding.FragmentMainBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

//        val textView: TextView = binding.sectionLabel
//        pageViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabs = arguments?.getSerializable(TABS_KEY) as Tabs
        pageViewModel = ViewModelProvider(this, PageViewModelFactory(tabs)).get(PageViewModel::class.java)

        pageViewModel.getGif()
        binding.buttonNext.setOnClickListener { pageViewModel.next() }
        binding.buttonPrev.setOnClickListener { pageViewModel.prev() }
        pageViewModel.gif.observe(viewLifecycleOwner, {
            binding.descriptionText.text = it.description
//            Toast.makeText(context, it.gifURL, Toast.LENGTH_LONG).show()
            Glide.with(this)
                .load(convertUrl(it.gifURL))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.abc_vector_test)
                .into(binding.gifImageView)
        })

    }

    private fun convertUrl(url : String) : String{
        return url.replace("http:", "https:")
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val TABS_KEY = "tabs"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(tabs: Tabs): PlaceholderFragment {
            val args = Bundle().apply { putSerializable(TABS_KEY, tabs) }
            return PlaceholderFragment().apply { arguments = args }
//            return PlaceholderFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(TABS_KEY, tabs)
//                }
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}