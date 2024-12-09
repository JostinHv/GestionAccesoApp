package com.jostin.gestionacceso.presentation.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jostin.gestionacceso.R
import com.jostin.gestionacceso.databinding.FragmentNfcBinding
import com.jostin.gestionacceso.presentation.viewmodel.NfcViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NfcFragment : Fragment() {

    private var _binding: FragmentNfcBinding? = null
    private val binding get() = _binding!!
    private var isClicked = false

    private val viewModel: NfcViewModel by viewModels()

    companion object {
        fun newInstance() = NfcFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializar View Binding
        _binding = FragmentNfcBinding.inflate(inflater, container, false)

        // Configurar el botón para cambiar el estado del badge
        binding.registrar.setOnClickListener {
            viewModel.toggleBadgeState(isClicked)
            val currentTime = getCurrentTime()

            if (isClicked) {
                // Si el botón ya fue presionado, revertir al estado original
                binding.registrar.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#22C55E"))  // Verde original
                binding.registrar.text = "Registrar Entrada"
                // Registrar hora de salida
                viewModel.updateSalidaTime(currentTime)
                // Deshabilitar el botón después de registrar salida
                binding.registrar.isEnabled = false
                // Ocultar el botón después de registrar salida
                binding.registrar.visibility = View.GONE
            } else {
                // Si el botón no ha sido presionado, cambiar a "Registrar Salida"
                binding.registrar.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#EF4444"))  // Rojo
                binding.registrar.text = "Registrar Salida"
                // Registrar hora de entrada
                viewModel.updateEntradaTime(currentTime)
            }

            // Alternar el estado de la variable isClicked
            isClicked = !isClicked
            // Si el card no está visible, lo mostramos
            binding.cardNfc.visibility = View.VISIBLE
        }

        // Observar los cambios de LiveData
        viewModel.badgeText.observe(viewLifecycleOwner, Observer { badge: String ->
            binding.cardBadge.text = badge  // Cambiar el texto del badge
            binding.cardBadge.setTextColor(if (badge == "Completado") Color.WHITE else Color.BLACK)
            val backgroundResource =
                if (badge == "Completado") R.drawable.badge_background_completed else R.drawable.badge_background
            binding.cardBadge.setBackgroundResource(backgroundResource)
        })

        // Observar el estado de los otros textos
        viewModel.lab.observe(viewLifecycleOwner, Observer { lab ->
            binding.labText.text = lab  // Actualiza el texto del lab
        })
        viewModel.course.observe(viewLifecycleOwner, Observer { course ->
            binding.courseText.text = course  // Actualiza el texto del curso
        })
        viewModel.date.observe(viewLifecycleOwner, Observer { date ->
            binding.dateText.text = date  // Actualiza el texto de la fecha
        })
        viewModel.entradaTime.observe(viewLifecycleOwner, Observer { entradaTime:String ->
            binding.entradaTime.text = entradaTime  // Actualiza el texto de entrada
        })
        viewModel.salidaTime.observe(viewLifecycleOwner, Observer { salidaTime :String->
            binding.salidaTime.text = salidaTime  // Actualiza el texto de salida
        })

        return binding.root
    }

    private fun getCurrentTime(): String {
        // Obtiene la fecha y hora actual
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)  // Devuelve la fecha y hora formateada
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Limpiar el binding
    }
}
