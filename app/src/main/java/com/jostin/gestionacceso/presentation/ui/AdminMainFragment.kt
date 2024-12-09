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
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.jostin.gestionacceso.R
import com.jostin.gestionacceso.databinding.FragmentAdminMainBinding
import com.jostin.gestionacceso.presentation.viewmodel.AdminMainViewModel

class AdminMainFragment : Fragment() {

    // ViewBinding y ViewModel
    private var _binding: FragmentAdminMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var drawerLayout: DrawerLayout

    private val viewModel: AdminMainViewModel by viewModels()

    // Navegación
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicializa el binding
        _binding = FragmentAdminMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el NavController desde el NavHostFragment
        navController = findNavController()
        drawerLayout = binding.drawerLayout
        val toolbar: Toolbar = binding.toolbar

        // Configuración de AppBarConfiguration (en caso de que sea necesario para navegación)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_inicio, R.id.nav_historial, R.id.nav_logout), // Puedes ajustar los destinos
            drawerLayout// Si tienes DrawerLayout, agrégalo aquí
        )

        // Configura la barra de acción
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // Configurar NavigationView o menú de navegación si es necesario
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Configura la barra de acción con NavController y AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            navController,
            appBarConfiguration
        )

        // Establece el ViewModel y el lifecycleOwner
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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

        // Si se requiere cargar un fragmento inicial
        if (savedInstanceState == null) {
            // Aquí puedes cargar el fragmento inicial que desees
            val initialFragment = DashboardFragment.newInstance() // Por ejemplo, un fragmento NFC
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, initialFragment)
                .commit()
        }

        // Manejo de navegación en el menú (si lo deseas)
        binding.navView.setNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_inicio -> DashboardFragment.newInstance()
                R.id.nav_historial -> HistorialFragment()
                R.id.nav_logout -> {
                    // Aquí es donde volvemos al LoginFragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment.newInstance())
                        .commitAllowingStateLoss()
                    navController.navigate(R.id.loginFragment)
                    return@setNavigationItemSelectedListener true
                }
                else -> null
            }

            fragment?.let {
                // Reemplazar el fragmento en el contenedor
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }

            // Cerrar el drawer si es necesario (si usas DrawerLayout)
            drawerLayout.closeDrawers() // Descomentar si usas DrawerLayout
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manejar el ítem de opciones (por ejemplo, para abrir el Drawer)
        return if (item.itemId == android.R.id.home) {
            drawerLayout.open() // Descomentar si usas DrawerLayout
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Limpiar el binding cuando se destruye la vista
    }
}
