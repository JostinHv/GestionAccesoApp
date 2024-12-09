package com.jostin.gestionacceso.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jostin.gestionacceso.R
import com.jostin.gestionacceso.databinding.FragmentLoginBinding
import com.jostin.gestionacceso.presentation.viewmodel.LoginResult
import com.jostin.gestionacceso.presentation.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Se creo la vista")

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeLoginResult()

        // Asocia el evento de login con el ViewModel
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            // Muestra en consola que se presiono el boton
            println("Se presiono el boton")
            viewModel.onLoginClick(username, password)
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is LoginResult.Admin -> {
                    // Navegar al panel de administrador
                    //findNavController().navigate(R.id.action_loginFragment_to_teacherDashboardFragment)
                }
                is LoginResult.Teacher -> {
                    // Navegar al panel de docente
                    findNavController().navigate(R.id.action_login_to_docente)

                }
                is LoginResult.Error -> {
                    Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}