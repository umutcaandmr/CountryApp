package com.umutdemir.countryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.umutdemir.countryapp.R
import com.umutdemir.countryapp.databinding.FragmentDetailBinding
import com.umutdemir.countryapp.util.downloadFromUrl
import com.umutdemir.countryapp.util.placeHolder
import com.umutdemir.countryapp.viewmodel.BaseViewModel
import com.umutdemir.countryapp.viewmodel.DetailViewModel


class DetailFragment : Fragment() {

    private var id = 0
    private lateinit var binding : FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            id =  DetailFragmentArgs.fromBundle(it).id
        }

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getDataFromRoom(id)

        viewModel.countryDetail.observe(viewLifecycleOwner){country->
            binding.countryDetailRegion.text = country.countryRegion
            binding.countryDetailLanguage.text = country.countryLanguage
            binding.countryDetailName.text = country.countryName
            binding.countryDetailCurrency.text = country.countryCurrency
            binding.countryDetailCapital.text = country.countryCapital
            binding.countryDetailImage.downloadFromUrl(country.countryImage, placeHolder(requireContext()))
        }



    }


}