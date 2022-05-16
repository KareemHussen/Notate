import android.annotation.SuppressLint
import com.example.notate.DB.NoteModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notate.R
import kotlinx.android.synthetic.main.note_item.view.*


class NoteAdapter() : androidx.recyclerview.widget.ListAdapter<NoteModel, NoteAdapter.viewHolder>(Diff()) {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var current = getItem(position)

        holder.itemView.apply {


            setOnClickListener {
                onItemClickListner?.let {
                    it(current)
                }
            }

            itemTitle.text = current.title

            if (itemTitle.text.isEmpty()){
                itemTitle.visibility = View.GONE
            } else {
                itemTitle.visibility = View.VISIBLE
            }

            itemContent.text = current.content

        }
    }

    private var onItemClickListner: ((NoteModel) -> Unit)? = null

    fun setOnItemClickListner(listner: (NoteModel) -> Unit) {
        onItemClickListner = listner
    }


}


class Diff() : DiffUtil.ItemCallback<NoteModel>() {
    override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem == newItem
    }

}