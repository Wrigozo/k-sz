package com.example.truthordare.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController


import com.example.truthordare.R
import com.example.truthordare.database.Player
import com.example.truthordare.database.PlayerDatabase
import com.example.truthordare.databinding.StartFragmentBinding

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel

    private lateinit var binding: StartFragmentBinding

    lateinit var btnNext: Button

    lateinit var btnAdd: Button

    lateinit var setPlayer: EditText

    var players: MutableList<Player> = mutableListOf<Player>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val viewModelFactory = StartViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(StartViewModel::class.java)

        btnNext=binding.btnNext

        btnAdd=binding.btnAdd

        setPlayer=binding.setPlayer

        binding.btnNext.setOnClickListener{

            goMain()
            //intent.putExtra("chosenPlayerName",setPlayer(players).name)

        }
        binding.btnAdd.setOnClickListener{
            binding.apply {
                viewModel.addDatabase(setPlayer.text.toString())
            }
            players.add(Player(0,setPlayer.text.toString(), 0))
            Toast.makeText(
                context, setPlayer.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.players.observe(viewLifecycleOwner, Observer {
            x -> binding.players.text=x.toString()
        })
        return binding.root
    }

    fun goMain(){
        val action=StartFragmentDirections.actionStartFragmentToMainFragment()
        view!!.findNavController().navigate(action)
    }

}
