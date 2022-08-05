package com.vila.demorequestwebapi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vila.demorequestwebapi.R
import com.vila.demorequestwebapi.databinding.FragmentListBinding
import com.vila.demorequestwebapi.domain.models.WebResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var animation : Animation
    private var _binding : FragmentListBinding? = null
    private val binding get() =  _binding!!
    private val viewModel : MainViewModel by activityViewModels()
    private val mAdapter = NotebookAdapter { notebook, view ->
        //val args = FragmentNavigatorExtras(view to notebook.image)
        viewModel.setSelectedNotebook(notebook)
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        init()
    }

    private fun init(){
        animation = AnimationUtils.loadAnimation(activity,R.anim.rotate)
        initRecyclerview()
        initObservers()
        initListeners()
    }

    private fun initRecyclerview() {
        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    private fun initListeners() {

    }

    private fun initObservers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notebooksharedFlow.collect { response ->
                    when(response){
                        is  WebResponse.Success -> {
                            viewModel.setProgressBarShared(false)
                            Log.d("webservice","${response.data}")
                            Log.d("webservice"," ..............")
                            viewModel.setListNotebooks(response.data)
                        }
                        is WebResponse.Error ->{
                            viewModel.setProgressBarShared(false)
                            Log.d("webservice","Error .......")
                            Log.d("webservice", response.exception)
                            Log.d("webservice"," ..............")
                        }
                        is WebResponse.Loading->{
                            viewModel.setProgressBarShared(true)
                            Log.d("webservice","Loading .......")
                        }
                        else->{}
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notebookList.collect { list ->
                    mAdapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.progressBarShared.collect { state ->
                    when(state){
                         false ->{
                             Log.d("webservice"," invisible..............")

                             binding.progress.visibility = View.INVISIBLE
                         }
                         true ->{
                             Log.d("webservice"," visible..............")

                             binding.progress.visibility = View.VISIBLE
                         }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}