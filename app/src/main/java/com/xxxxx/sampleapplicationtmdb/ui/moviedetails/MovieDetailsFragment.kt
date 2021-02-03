package com.xxxxx.sampleapplicationtmdb.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.xxxxx.sampleapplicationtmdb.API_IMAGE_URL
import com.xxxxx.sampleapplicationtmdb.MOVIE_ID
import com.xxxxx.sampleapplicationtmdb.data.MovieDetails
import com.xxxxx.sampleapplicationtmdb.databinding.MovieDetailsFragmentBinding
import com.xxxxx.sampleapplicationtmdb.ui.MainViewModelFactory
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(private val mainViewModelFactory: MainViewModelFactory) :
    Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: MovieDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel =
            ViewModelProvider(this, mainViewModelFactory).get(MovieDetailsViewModel::class.java)
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let {
            viewModel.loadMovieDetails(it)
        }

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isVisible ->
            binding.detailsProgressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

        viewModel.errorLoadingDetails.observe(viewLifecycleOwner, Observer { isError ->
            binding.detailsFetchError.visibility = if (isError) View.VISIBLE else View.GONE
        })
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer { selectedMovie ->
            bindMovieToView(selectedMovie)
        })
    }

    private fun bindMovieToView(selectedMovie: MovieDetails) {
        activity?.title = selectedMovie.title

        if (selectedMovie.posterPath != null) {
            Glide.with(requireContext()).load(API_IMAGE_URL + selectedMovie.posterPath)
                .into(binding.image)
        }
        binding.tagline.text = selectedMovie.tagline
        binding.overview.text = selectedMovie.overview
        binding.popularity.text = selectedMovie.popularity.toString()
        binding.voteAverage.text = selectedMovie.voteAverage.toString()
        binding.voteCount.text = selectedMovie.voteCount.toString()
        binding.revenue.text = selectedMovie.revenue.toString()
        binding.runtime.text = selectedMovie.runtime.toString()
        binding.adult.text = selectedMovie.adult.toString()
        binding.releaseDate.text = selectedMovie.releaseDate
        binding.originalTitle.text = selectedMovie.originalTitle
        binding.originaLanguage.text = selectedMovie.originaLanguage
        binding.status.text = selectedMovie.status
        binding.homepage.text = selectedMovie.homepage

        selectedMovie.productionCompanies?.let {
            for (i in it.indices) {
                val tv = TextView(context)
                tv.text = it[i].name + ", " + it[i].originCountry
                binding.productionCompaniesContainer.addView(tv)
            }
        }
        selectedMovie.productionCountries?.let {
            for (i in it.indices) {
                val tv = TextView(context)
                tv.text = it[i].name
                binding.productionCountriesContainer.addView(tv)
            }
        }
        selectedMovie.spokenLanguages?.let {
            for (i in it.indices) {
                val tv = TextView(context)
                tv.text = it[i].name
                binding.spokenLanguagesContainer.addView(tv)
            }
        }
        selectedMovie.genres?.let {
            for (i in it.indices) {
                val tv = TextView(context)
                tv.text = it[i].name
                binding.genresContainer.addView(tv)
            }
        }
    }

}
