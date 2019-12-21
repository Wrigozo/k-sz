package com.example.truthordare.json

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.truthordare.R
import com.example.truthordare.api.JsonApi
import com.example.truthordare.api.JsonModel
import com.example.truthordare.databinding.JsonFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JsonFragment : Fragment() {

    companion object {
        fun newInstance() = JsonFragment()
    }

    private lateinit var viewModel: JsonViewModel

    private lateinit var binding: JsonFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.json_fragment, container, false)
        val adapter = JsonAdapter(JsonListener { jsonId ->
            Toast.makeText(context, "${jsonId}", Toast.LENGTH_LONG).show()
        })
        binding.jsonList.adapter= adapter
        JsonApi().getData().enqueue(object : Callback<List<JsonModel>> {

            override fun onFailure(call: Call<List<JsonModel>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }


            override fun onResponse(call: Call<List<JsonModel>>, response: Response<List<JsonModel>>) {
                val inf = response.body()

                inf?.let {
                    adapter.addHeaderAndSubmitList(it)
                }
            }


        })


        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(JsonViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
