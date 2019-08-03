package me.loidsemus.debtomania.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.android.synthetic.main.add_fragment.view.*
import kotlinx.android.synthetic.main.add_fragment.view.amountInput
import me.loidsemus.debtomania.R
import me.loidsemus.debtomania.SharedViewModel
import me.loidsemus.debtomania.database.Debt


class AddFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.add_fragment, container, false)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        root.doneButton.setOnClickListener {
            if (validate()) {
                val debt = Debt(
                    0,
                    root.amountInput.text.toString().toDouble(),
                    root.nameInput.text.toString(),
                    System.currentTimeMillis()
                )
                viewModel.insert(debt)
                findNavController().navigateUp()
            }
        }

        return root
    }

    private fun validate(): Boolean {
        var valid = false

        if (amountInput.text.toString() == "") {
            amountInputLayout.isErrorEnabled = true
            amountInputLayout.error = getString(R.string.add_error_no_amount)
        } else {
            amountInputLayout.isErrorEnabled = false
            amountInputLayout.error = null
        }

        if (nameInput.text.toString() == "") {
            nameInputLayout.isErrorEnabled = true
            nameInputLayout.error = getString(R.string.add_error_no_name)
        } else {
            nameInputLayout.isErrorEnabled = false
            nameInputLayout.error = null
        }

        if (amountInput.text.toString() != "" && nameInput.text.toString() != "") {
            valid = true
        }

        return valid
    }
}