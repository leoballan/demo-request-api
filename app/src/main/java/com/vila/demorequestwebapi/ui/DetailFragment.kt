package com.vila.demorequestwebapi.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.vila.demorequestwebapi.R
import com.vila.demorequestwebapi.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding
    private val viewmodel : MainViewModel by activityViewModels()
  //  private val args : DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        init()
    }


    private fun init(){
       // setSharedElementTransiiton()
       // postponeEnterTransition()
        binding!!.image.apply {
  /*          transitionName = args.tName
            startEnterTransitionAfterLoadingImage(args.tName,this)*/
        }
        initObeserber()
    }

    private fun initObeserber() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.selectedNotebookState.collect {  notebook ->
                    Log.d("webservice","notebook seleccionada .......")
                    Log.d("webservice","titulo ... ${notebook.title}")
                    Log.d("webservice"," ..............")
                    Log.d("webservice","descrip ... ${notebook.description}")
                    Log.d("webservice"," ..............")
                    Log.d("webservice","image ... ${notebook.image}")
                    binding?.let{
                        it.tittle.text = notebook.title
                        it.description.text = notebook.description
                        Glide.with(activity?.applicationContext!!)
                            .load(notebook.image)
                            .placeholder(R.drawable.ic_baseline_downloading_24)
                            .into(it.image)
                    }
                }
            }
        }
    }

    private fun setSharedElementTransiiton() {

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(url:String, view:ImageView){
        Glide.with(this)
            .load(url)
            .dontAnimate() // 1
            .listener(object : RequestListener<Drawable> { // 2
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}