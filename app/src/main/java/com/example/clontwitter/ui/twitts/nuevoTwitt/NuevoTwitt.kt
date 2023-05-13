package com.example.clontwitter.ui.twitts.nuevoTwitt

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
import com.example.clontwitter.R
import com.example.clontwitter.databinding.FragmentNuevoTwittBinding
import com.example.clontwitter.ui.twitts.viewModel.TwittViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoTwitt.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoTwitt : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val twittViewModel:TwittViewModel by activityViewModels {
        TwittViewModel.Factory
    }

    private lateinit var binding:FragmentNuevoTwittBinding

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
        binding = FragmentNuevoTwittBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnGuardar.setOnClickListener{
            if(twittViewModel.descripcion.value.isNullOrBlank()){
                Toast.makeText(context,"Ingrese una descripción!",Toast.LENGTH_SHORT).show()
            }else{
                twittViewModel.guardarInformacion()
                it.findNavController().navigate(R.id.accion_nuevoTwitt_a_twitts)
            }

        }

        binding.btnCancelar.setOnClickListener{
            it.findNavController().navigate(R.id.accion_nuevoTwitt_a_twitts)
        }

        setViewModel()
    }

    private fun setViewModel(){
        binding.viewmodel = twittViewModel
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoTwitt.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoTwitt().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}