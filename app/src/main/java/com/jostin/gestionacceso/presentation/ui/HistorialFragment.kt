package com.jostin.gestionacceso.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jostin.gestionacceso.databinding.FragmentHistorialBinding
import com.jostin.gestionacceso.presentation.viewmodel.HistorialViewModel
import com.jostin.gestionacceso.domain.models.Access

class HistorialFragment : Fragment() {

    private var _binding: FragmentHistorialBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HistorialViewModel
    private lateinit var accesoAdapter: AccesoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        accesoAdapter = AccesoAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = accesoAdapter

        // Obtener ViewModel
        viewModel = ViewModelProvider(this).get(HistorialViewModel::class.java)

        // Observar los datos del ViewModel
        viewModel.accessList.observe(viewLifecycleOwner, { historialList ->
            accesoAdapter.submitList(historialList)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
