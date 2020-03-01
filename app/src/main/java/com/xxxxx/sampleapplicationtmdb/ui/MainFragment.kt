package com.xxxxx.sampleapplicationtmdb.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.sampleapplicationtmdb.App
import com.xxxxx.sampleapplicationtmdb.R
import com.xxxxx.sampleapplicationtmdb.databinding.MainFragmentBinding
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as App).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let { //to share the same view model between fragments
            viewModel = ViewModelProvider(it, mainViewModelFactory).get(MainViewModel::class.java)
            //viewModel.loadPopularMovies()
            it.title = getString(R.string.app_name)
        }

        val binding = MainFragmentBinding.inflate(inflater, container, false)
        val adapter = MainListAdapter(
            ItemClickListener { item ->
                viewModel.onItemSelected(item.id)
                showDetailsFragment()
            })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.mainRecyclerView.adapter = adapter

        viewModel.movieItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.setItems(items)
        })

        return binding.root
    }

    private fun showDetailsFragment() {
        val detailsFragment = DetailsFragment.newInstance()
        val transaction = activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, detailsFragment)
            addToBackStack(null)
        }
        transaction.commit()
    }

}
