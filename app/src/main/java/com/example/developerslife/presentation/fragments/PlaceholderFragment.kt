package com.example.developerslife.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.developerslife.databinding.FragmentMainBinding
import com.example.developerslife.presentation.Tabs
import com.example.developerslife.presentation.viewmodels.PageViewModel
import com.example.developerslife.presentation.viewmodels.PageViewModelFactory

class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = arguments?.getSerializable(TABS_NAME) as Tabs
        pageViewModel =
            ViewModelProvider(this, PageViewModelFactory(tabs)).get(PageViewModel::class.java)

        pageViewModel.getGif()

        binding.buttonNext.setOnClickListener { pageViewModel.next() }
        binding.buttonPrev.setOnClickListener { pageViewModel.prev() }

        pageViewModel.isFirst.observe(viewLifecycleOwner) {
            pageViewModel.isFirstIndex()
            binding.buttonPrev.isEnabled = it
        }

        pageViewModel.gif.observe(viewLifecycleOwner) {
            binding.descriptionText.text = it.description
            Glide.with(this)
                .load(convertUrl(it.gifURL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getCircularProgressDrawable())
                .into(binding.gifImageView)
        }
    }

    private fun getCircularProgressDrawable(): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context?.applicationContext!!)
        circularProgressDrawable.strokeWidth = STROKE_WIDTH
        circularProgressDrawable.centerRadius = CENTER_RADIUS
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    private fun convertUrl(url: String): String {
        return url.replace("http:", "https:")
    }

    companion object {

        private const val STROKE_WIDTH = 10f
        private const val CENTER_RADIUS = 50f
        private const val TABS_NAME = "tabs"

        @JvmStatic
        fun newInstance(tabs: Tabs): PlaceholderFragment {
            val args = Bundle().apply { putSerializable(TABS_NAME, tabs) }
            return PlaceholderFragment().apply { arguments = args }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

