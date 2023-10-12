package com.umutdemir.countryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.umutdemir.countryapp.R
import com.umutdemir.countryapp.adapter.CountryAdapter
import com.umutdemir.countryapp.databinding.FragmentCountryListBinding
import com.umutdemir.countryapp.viewmodel.CountryListViewModel


class CountryListFragment : Fragment() {


    private lateinit var binding : FragmentCountryListBinding
    private lateinit var viewModel: CountryListViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountryListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[CountryListViewModel::class.java]
        viewModel.getDataFromSQLite()

        binding.countryList.adapter = countryAdapter
        binding.countryList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.countries.observe(viewLifecycleOwner){
            it?.let {
                countryAdapter.updateData(it)
                binding.countryLoading.visibility = View.GONE
                binding.countryList.visibility = View.VISIBLE
                binding.countryError.visibility = View.GONE
            }
        }

        viewModel.countryLoading.observe(viewLifecycleOwner){
            if(it){
                binding.countryLoading.visibility = View.VISIBLE
                binding.countryList.visibility = View.GONE
                binding.countryError.visibility = View.GONE
            }
        }

        viewModel.countryError.observe(viewLifecycleOwner){
            if(it){
                binding.countryLoading.visibility = View.GONE
                binding.countryList.visibility = View.GONE
                binding.countryError.visibility = View.VISIBLE
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDataFromSQLite()
            binding.swipeRefreshLayout.isRefreshing = false
        }


    }







}
