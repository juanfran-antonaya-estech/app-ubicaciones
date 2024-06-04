package com.juanfra.appubicaciones.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.juanfra.appubicaciones.R
import com.juanfra.appubicaciones.data.MapViewModel
import com.juanfra.appubicaciones.data.model.Fuente
import com.juanfra.appubicaciones.databinding.BottomsheetfragmentBinding

class BottomSheet() : BottomSheetDialogFragment() {

    companion object {
        lateinit var viewModel: MapViewModel
        const val TAG = "BottomSheet"
    }

    private lateinit var binding: BottomsheetfragmentBinding
    val viewModel : MapViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetfragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.actualFuente.observe(viewLifecycleOwner){ fuente ->
            val formulario = binding.includform
            if (fuente != null) {
                formulario.tiNombre.setText(fuente.nombre)
                formulario.tiBoquillas.setText(fuente.boquillas.toString())
                formulario.rbPuntuacion.rating = fuente.puntuacion.toFloat()
                formulario.cbWorks.isChecked = fuente.funciona
                when (fuente.temperatura) {
                    "Hot" -> formulario.radioGroup.check(R.id.rbHot)
                    "Med" -> formulario.radioGroup.check(R.id.rbNormal)
                    "Cold" -> formulario.radioGroup.check(R.id.rbCold)
                }
                formulario.btSave.setText("Guardar")
                formulario.btSave.setOnClickListener{
                    fuente.nombre = formulario.tiNombre.text.toString()
                    fuente.boquillas = formulario.tiBoquillas.text.toString().toInt()
                    fuente.puntuacion = formulario.rbPuntuacion.rating.toDouble()
                    fuente.funciona = formulario.cbWorks.isChecked
                    fuente.temperatura = when (formulario.radioGroup.checkedRadioButtonId) {
                        R.id.rbHot -> "Hot"
                        R.id.rbNormal -> "Med"
                        R.id.rbCold -> "Cold"
                        else -> "Med"
                    }
                    fuente.visitada = true
                    viewModel.actualizarFuente(fuente)
                    dismiss()
                }

                formulario.btCancel.setText("Borrar")
                formulario.btCancel.setOnClickListener{
                    viewModel.borrarFuente(fuente)
                    dismiss()
                }
            } else {
                formulario.btSave.setText("Crear")
                formulario.btSave.setOnClickListener{
                    val fuente = Fuente(
                        lat= viewModel.actualLatLng.value!!.latitude,
                        lng= viewModel.actualLatLng.value!!.longitude,
                        nombre = formulario.tiNombre.text.toString(),
                        boquillas = formulario.tiBoquillas.text.toString().toInt(),
                        puntuacion = formulario.rbPuntuacion.rating.toDouble(),
                        funciona = formulario.cbWorks.isChecked,
                        temperatura = when (formulario.radioGroup.checkedRadioButtonId) {
                            R.id.rbHot -> "Hot"
                            R.id.rbNormal -> "Med"
                            R.id.rbCold -> "Cold"
                            else -> "Med"},
                        visitada = true
                    )
                    viewModel.addFuente(fuente)
                    dismiss()
                }
                formulario.btCancel.setText("Cerrar")
                formulario.btCancel.setOnClickListener{
                    dismiss()
                }
            }
        }
    }

}