package me.loidsemus.debtomania.screens.debts

import android.content.Context
import android.graphics.Canvas
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import me.loidsemus.debtomania.R

class SwipeCallback(private val context: Context, private val adapter: DebtListAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT) {

    private val background = context.getDrawable(R.drawable.swipe_background)
    private val swipeDeleteColor: TypedValue = TypedValue()
    private val swipeArchiveColor: TypedValue = TypedValue()

    init {
        val theme = context.theme

        theme.resolveAttribute(R.attr.swipeDeleteColor, swipeDeleteColor, true)
        theme.resolveAttribute(R.attr.swipeArchiveColor, swipeArchiveColor, true)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if(direction == ItemTouchHelper.RIGHT) {
            adapter.deleteDebt(position)
            Snackbar.make(viewHolder.itemView, context.getString(R.string.debt_deleted), Snackbar.LENGTH_SHORT).show()
        } else if(direction == ItemTouchHelper.LEFT) {
            adapter.archiveDebt(position)
            Snackbar.make(viewHolder.itemView, context.getString(R.string.debt_archived), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 30

        background?.let {
            when {
                dX > 0 -> { // Swiping right
                    it.setTint(swipeDeleteColor.data)
                    it.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + (dX.toInt()) + backgroundCornerOffset,
                        itemView.bottom
                    )
                }
                dX < 0 -> { // Swiping left
                    it.setTint(swipeArchiveColor.data)
                    it.setBounds(
                        itemView.right + (dX.toInt()) - backgroundCornerOffset,
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                }

                else -> // No swipe
                    it.setBounds(0, 0, 0, 0)
            }
            it.draw(c)
        }
    }
}