package com.example.clontwitter.ui.twitts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clontwitter.R
import com.example.clontwitter.databinding.FragmentTwittsBinding
import com.example.clontwitter.modelo.TwittModelo
import com.example.clontwitter.ui.twitts.recyclerView.TwitterAdapter
import com.example.clontwitter.ui.twitts.viewModel.TwittViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Twitts.newInstance] factory method to
 * create an instance of this fragment.
 */
class Twitts : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val twittViewModel:TwittViewModel by activityViewModels {
        TwittViewModel.Factory
    }
    private lateinit var adapter: TwitterAdapter
    private lateinit var binding: FragmentTwittsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwittsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observadorLista()
        setRecyclerView(view)
        binding.btnAgregar.setOnClickListener {
            it.findNavController().navigate(R.id.accion_twitts_a_nuevoTwitt)
        }
        binding.btnPerfil.setOnClickListener {
            it.findNavController().navigate(R.id.accion_twitts_a_perfil)
        }
    }

    private fun setRecyclerView(view:View){
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        twittViewModel.obtenerTwitts()
        adapter = TwitterAdapter(::eliminarTwitt,::editarTwitt)
        binding.recyclerView.adapter = adapter

        //Cargar datos
        twittViewModel.obtenerTwitts()
    }

    private fun observadorLista(){
        twittViewModel.twittsLiveData.observe(viewLifecycleOwner, { twitts ->
            adapter.setData(twitts)
        })
    }

    private fun eliminarTwitt(twitt: TwittModelo){
        twittViewModel.eliminarTwitt(twitt)
    }

    private fun editarTwitt(twitt: TwittModelo){
        twittViewModel.estado.value = TwittViewModel.EDITAR_TWITT
        twittViewModel.setTwittActual(twitt)
        findNavController().navigate(R.id.accion_twitts_a_nuevoTwitt)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Twitts.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Twitts().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}