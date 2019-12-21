package com.example.truthordare.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.lifecycle.Observer
import com.example.truthordare.R
import com.example.truthordare.database.PlayerDatabase
import com.example.truthordare.databinding.RankFragmentBinding

class RankFragment : Fragment() {
    private lateinit var binding: RankFragmentBinding
    companion object {
        fun newInstance() = RankFragment()
    }

    private lateinit var viewModel: RankViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rank_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val viewModelFactory = RankViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(RankViewModel::class.java)
        viewModel.players.observe(viewLifecycleOwner, Observer {
                x -> binding.ranklist.text=x.toString()
        })
        binding.ranklist.text=dataSource.getAllPlayers().toString()
        binding.goToJson.setOnClickListener(){
            goJson()
            viewModel.deletePlayers()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RankViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun goJson(){
        val action=RankFragmentDirections.actionRankFragmentToJsonFragment()
        view!!.findNavController().navigate(action)
    }


}
