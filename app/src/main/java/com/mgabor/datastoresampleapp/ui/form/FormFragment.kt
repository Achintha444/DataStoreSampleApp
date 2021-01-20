package com.mgabor.datastoresampleapp.ui.form

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mgabor.datastoresampleapp.R
import com.mgabor.datastoresampleapp.databinding.FragmentFormBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FormFragment : Fragment() {

    private val viewModel: FormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFormBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_form, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private var dateSetListener =
        OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()

            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, monthOfYear)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.birthDay.postValue(selectedDate.timeInMillis)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDate = Calendar.getInstance()
        view.findViewById<View>(R.id.birthdayEditText).setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener, selectedDate
                    .get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}