package me.loidsemus.debtomania.screens.debts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.debts_fragment.*
import kotlinx.android.synthetic.main.debts_fragment.view.*
import me.loidsemus.debtomania.R
import me.loidsemus.debtomania.SharedViewModel


class DebtsFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.debts_fragment, container, false)

        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val adapter = DebtListAdapter(activity!!, viewModel)
        val recyclerView = root.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        ItemTouchHelper(SwipeCallback(activity!!, adapter)).attachToRecyclerView(recyclerView)

        viewModel.allActiveDebts.observe(this, Observer { debts ->
            if (!debts.isNullOrEmpty()) {
                recyclerView.visibility = View.VISIBLE
                noDebts.visibility = View.GONE
                debts.let { adapter.setDebts(it) }
            } else if (debts.isEmpty()) {
                noDebts.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        })

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_item -> findNavController().navigate(R.id.action_debtsFragment_to_addFragment)
            R.id.action_settings -> findNavController().navigate(R.id.open_settings_fragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
