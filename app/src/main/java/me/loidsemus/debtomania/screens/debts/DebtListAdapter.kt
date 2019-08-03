package me.loidsemus.debtomania.screens.debts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.debt_item.view.*
import me.loidsemus.debtomania.R
import me.loidsemus.debtomania.SharedViewModel
import me.loidsemus.debtomania.database.Debt
import java.text.DateFormat
import java.text.NumberFormat


class DebtListAdapter(private val context: Context, private val viewModel: SharedViewModel) :
    RecyclerView.Adapter<DebtListAdapter.DebtViewHolder>() {

    private var debts: MutableList<Debt> = emptyList<Debt>().toMutableList()

    inner class DebtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debt_item, parent, false)

        return DebtViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val current = debts[position]
        holder.itemView.amountTextView.text = NumberFormat.getInstance().format(current.amount)
        holder.itemView.detailsTextView.text = context.getString(
            R.string.debt_details,
            current.person,
            DateFormat.getDateInstance().format(current.dateMillis)
        )
    }

    internal fun setDebts(newDebts: List<Debt>) {

        //Calculate a diff, update the list and then push changes
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return debts[oldItemPosition].id == newDebts[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return debts[oldItemPosition] == newDebts[newItemPosition]
            }

            override fun getOldListSize() = debts.size
            override fun getNewListSize() = newDebts.size
        })

        debts.clear()
        debts.addAll(newDebts)

        diff.dispatchUpdatesTo(this)
    }

    fun deleteDebt(position: Int) {
        viewModel.delete(debts[position])
    }

    fun archiveDebt(position: Int) {
        val current = debts[position]
        current.archived = true
        viewModel.update(current)
    }

    override fun getItemCount() = debts.size
}