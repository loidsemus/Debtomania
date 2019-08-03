package me.loidsemus.debtomania.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.android.synthetic.main.add_fragment.view.*
import kotlinx.android.synthetic.main.add_fragment.view.amountInput
import me.loidsemus.debtomania.R
import me.loidsemus.debtomania.SharedViewModel
import me.loidsemus.debtomania.database.Debt
import me.loidsemus.debtomania.databinding.AddFragmentBinding


class AddFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: AddFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        binding.viewModel = viewModel

        binding.root.doneButton.setOnClickListener {
            if (validate()) {
                val debt = Debt(
                    0,
                    binding.root.amountInput.text.toString().toDouble(),
                    binding.root.nameInput.text.toString(),
                    System.currentTimeMillis()
                )
                viewModel.insert(debt)
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    private fun validate(): Boolean {
        var valid = false

        if (amountInput.text.toString() == "") {
            amountInputLayout.error = "You need to specify an amount"
        } else {
            amountInputLayout.error = null
        }

        if (nameInput.text.toString() == "") {
            nameInputLayout.error = "You need to specify a name"
        } else {
            nameInputLayout.error = null
        }

        if (amountInput.text.toString() != "" && nameInput.text.toString() != "") {
            valid = true
        }

        return valid
    }
}