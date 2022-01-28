package com.example.developerslife.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.location.GnssAntennaInfo
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.developerslife.MainActivity
import com.example.developerslife.R
import com.example.developerslife.R.color.red

import com.example.developerslife.databinding.FragmentMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceholderFragment : Fragment()  {

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
        pageViewModel = ViewModelProvider(this, PageViewModelFactory(tabs)).get(PageViewModel::class.java)

//        pageViewModel.getNewGif.observe(viewLifecycleOwner,{
//            binding.buttonNext.isEnabled = it
//            binding.buttonPrev.isEnabled = it
//        })

        pageViewModel.getGif()

//        val butNext = view.findViewById<Button>(R.id.buttonNext)
//        butNext.setOnClickListener(View.OnClickListener {
//            fun onClick(view: View){
//                activity?.findViewById<Button>(R.id.buttonActivityNext)?.setOnClickListener {
//                    pageViewModel.next()
//                }
//            }
//        })
//        activity?.findViewById<Button>(R.id.buttonActivityNext)?.setOnClickListener {
//            pageViewModel.next()
//        }
//
//        activity?.findViewById<Button>(R.id.buttonActivityPrev)?.setOnClickListener {
//            pageViewModel.prev()
//        }

//        buttonEffect(binding.buttonNext)
//        buttonEffect(binding.buttonPrev)

        binding.buttonNext.setOnClickListener { pageViewModel.next() }
        binding.buttonPrev.setOnClickListener { pageViewModel.prev() }

        pageViewModel.isFirst.observe(viewLifecycleOwner, {
            pageViewModel.isFirstIndex()
            binding.buttonPrev.isEnabled = it
        })

        pageViewModel.gif.observe(viewLifecycleOwner, {
            binding.descriptionText.text = it.description
            Glide.with(this)
                .load(convertUrl(it.gifURL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getCircularProgressDrawable())
                .into(binding.gifImageView)
        })

    }

    private fun getCircularProgressDrawable() : CircularProgressDrawable  {
        val circularProgressDrawable = CircularProgressDrawable(context?.applicationContext!!)
        circularProgressDrawable.strokeWidth = STROKE_WIDTH
        circularProgressDrawable.centerRadius = CENTER_RADIUS
        circularProgressDrawable.start()
        return circularProgressDrawable
    }


//    fun onClick(p0: View?) {
//        val tabs = arguments?.getSerializable(TABS_NAME) as Tabs
//        pageViewModel = ViewModelProvider(this, PageViewModelFactory(tabs)).get(PageViewModel::class.java)
//        pageViewModel.next()
//    }

    @SuppressLint("ClickableViewAccessibility")
    fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }

//    private fun Fragment.vibratePhone() {
//        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if (Build.VERSION.SDK_INT >= 26) {
//            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            vibrator.vibrate(200)
//        }
//    }

    private fun convertUrl(url : String) : String{
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

//    public class MyListener(viewModel: PageViewModel) : View.OnClickListener {
//        override fun onClick(p0: View?) {
//
//        }
//    }
}