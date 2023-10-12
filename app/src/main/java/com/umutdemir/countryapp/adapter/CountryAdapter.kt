package com.umutdemir.countryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.umutdemir.countryapp.databinding.ItemCountryBinding
import com.umutdemir.countryapp.model.Country
import com.umutdemir.countryapp.util.downloadFromUrl
import com.umutdemir.countryapp.util.placeHolder
import com.umutdemir.countryapp.view.CountryListFragmentDirections

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.countryName.text = countryList[position].countryName
        holder.binding.countryRegion.text = countryList[position].countryRegion
        //image
        holder.binding.countryImage.downloadFromUrl(countryList[position].countryImage, placeHolder(holder.itemView.context))


        holder.binding.countryLinear.setOnClickListener{
            val action = CountryListFragmentDirections.actionCountryListFragmentToDetailFragment(countryList[position].id)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun updateData(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()

    }
}