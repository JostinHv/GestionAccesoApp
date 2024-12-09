package com.jostin.gestionacceso.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jostin.gestionacceso.R
import com.jostin.gestionacceso.databinding.ItemNfcRecordBinding
import com.jostin.gestionacceso.domain.models.NfcRecord

// Adapter para mostrar los registros NFC
class NfcRecordAdapter(private val records: List<NfcRecord>) : RecyclerView.Adapter<NfcRecordAdapter.NfcRecordViewHolder>() {

    // ViewHolder para cada ítem de la lista
    inner class NfcRecordViewHolder(val binding: ItemNfcRecordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NfcRecordViewHolder {
        val binding = ItemNfcRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NfcRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NfcRecordViewHolder, position: Int) {
        val record = records[position]
        with(holder.binding) {
            labText.text = record.lab
            courseText.text = record.course
            dateText.text = record.date
            entradaTime.text = record.entradaTime
            salidaTime.text = record.salidaTime
        }
    }

    override fun getItemCount(): Int = records.size
}
