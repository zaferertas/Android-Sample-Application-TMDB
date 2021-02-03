package com.xxxxx.sampleapplicationtmdb.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xxxxx.sampleapplicationtmdb.MOVIE_ID
import com.xxxxx.sampleapplicationtmdb.R
import com.xxxxx.sampleapplicationtmdb.data.MovieItem
import com.xxxxx.sampleapplicationtmdb.databinding.MovieListFragmentBinding
import com.xxxxx.sampleapplicationtmdb.ui.MainViewModelFactory
import com.xxxxx.sampleapplicationtmdb.ui.moviedetails.MovieDetailsFragment
import javax.inject.Inject

class MovieListFragment @Inject constructor(private val mainViewModelFactory: MainViewModelFactory) :
    Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: MovieListFragmentBinding
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.title = getString(R.string.app_name)

        adapter = MovieListAdapter(
            object :
                MovieListAdapter.ItemClickListener {
                override fun onClick(item: MovieItem) {
                    showDetailsFragment(item.id)
                }
            })

        binding = MovieListFragmentBinding.inflate(inflater, container, false)
        binding.mainRecyclerView.adapter = adapter

        viewModel =
            ViewModelProvider(this, mainViewModelFactory).get(MovieListViewModel::class.java)

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.movieItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.setItems(items)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isVisible ->
            binding.mainProgressBar.visibility = if (isVisible) VISIBLE else GONE
        })

        viewModel.errorLoadingList.observe(viewLifecycleOwner, Observer { isError ->
            binding.mainFetchError.visibility = if (isError) VISIBLE else GONE
        })
    }

    private fun showDetailsFragment(movieId: Int) {

        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, MovieDetailsFragment::class.java, bundle)
            .addToBackStack(null)
            .commit()
    }

}
