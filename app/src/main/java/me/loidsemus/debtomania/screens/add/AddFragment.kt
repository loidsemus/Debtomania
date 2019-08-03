package me.loidsemus.debtomania.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.add_fragment.view.*
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
            val debt = Debt(
                0,
                binding.root.amountInput.text.toString().toDouble(),
                binding.root.nameInput.text.toString(),
                System.currentTimeMillis()
            )
            viewModel.insert(debt)
            findNavController().navigateUp()
        }

        return binding.root
    }
}