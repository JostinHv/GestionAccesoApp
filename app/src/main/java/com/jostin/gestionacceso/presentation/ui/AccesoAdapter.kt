package com.jostin.gestionacceso.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jostin.gestionacceso.databinding.ItemAccesoBinding
import com.jostin.gestionacceso.domain.models.Access
import java.text.SimpleDateFormat
import java.util.Locale

class AccesoAdapter : ListAdapter<Access, AccesoAdapter.AccesoViewHolder>(AccesoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccesoViewHolder {
        val binding = ItemAccesoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccesoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccesoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AccesoViewHolder(private val binding: ItemAccesoBinding) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        @SuppressLint("SetTextI18n")
        fun bind(acceso: Access) {
            binding.tvLaboratorio.text = "Laboratorio: ${acceso.labName}"
            binding.tvCurso.text = "Curso: ${acceso.courseName}"
            binding.tvFecha.text = "Fecha: ${dateFormat.format(acceso.entryTime.toDate())}"
            binding.tvHoraEntrada.text = "Entrada: ${timeFormat.format(acceso.entryTime.toDate())}"
            binding.tvHoraSalida.text = if (acceso.exitTime != null) {
                "Salida: ${timeFormat.format(acceso.exitTime.toDate())}"
            } else {
                "Salida: --"
            }
        }
    }

    class AccesoDiffCallback : DiffUtil.ItemCallback<Access>() {
        override fun areItemsTheSame(oldItem: Access, newItem: Access): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Access, newItem: Access): Boolean {
            return oldItem == newItem
        }
    }
}
