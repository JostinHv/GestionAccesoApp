package com.jostin.gestionacceso.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.jostin.gestionacceso.R
import com.jostin.gestionacceso.databinding.FragmentDocenteMainBinding
import com.jostin.gestionacceso.presentation.viewmodel.DocenteViewModel

class DocenteMainFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var _binding: FragmentDocenteMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DocenteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocenteMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el DrawerLayout y NavigationView
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val toolbar: Toolbar = binding.toolbar

        // Configurar NavController desde el NavHostFragment
        navController = findNavController()

        // Configurar AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_historial, R.id.nav_tarjetas, R.id.nav_soporte),
            drawerLayout
        )

        // Configura Toolbar y ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Configurar el DrawerToggle para abrir y cerrar el Drawer
        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open, // Texto accesible
            R.string.navigation_drawer_close  // Texto accesible
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configura el NavigationView
        NavigationUI.setupWithNavController(navView, navController)

        // Configura el NavController en el AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController,
            appBarConfiguration
        )

        // Establece el ViewModel y el lifecycleOwner
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Si el fragmento NFC no está cargado, lo cargamos en el contenedor
        if (savedInstanceState == null) {
            val nfcFragment = NfcFragment.newInstance()

            // Usar parentFragmentManager para añadir el fragmento
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, nfcFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        // Configurar el NavigationView

        navView.setNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_inicio -> NfcFragment.newInstance()
                R.id.nav_historial -> HistorialFragment()
                //R.id.nav_tarjetas -> TarjetasFragment() // Fragmento para "Tarjetas"
                //R.id.nav_soporte -> SoporteFragment()   // Fragmento para "Soporte"
                else -> null
            }

            fragment?.let {
                // Reemplazar el fragmento en el contenedor y limpiar el anterior
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }

            // Cerrar el DrawerLayout
            drawerLayout.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.open()
            true
        } else {
            NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
