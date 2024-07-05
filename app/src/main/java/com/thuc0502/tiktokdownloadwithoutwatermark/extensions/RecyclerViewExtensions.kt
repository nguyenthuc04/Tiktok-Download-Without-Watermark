package com.thuc0502.tiktokdownloadwithoutwatermark.extensions

import androidx.recyclerview.widget.RecyclerView

/**
 * Thiết lập RecyclerView với LayoutManager và Adapter
 * @receiver RecyclerView
 * @param layoutManager RecyclerView.LayoutManager
 * @param adapter RecyclerView.Adapter<*>
 */
fun RecyclerView.setup(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

/**
 * Thêm sự kiện click cho RecyclerView.ViewHolder
 * @receiver RecyclerView.ViewHolder
 * @param event Function2<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] Int, Unit>
 * @return T
 */
fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}

/**
 * Thông báo dữ liệu thay đổi cho RecyclerView.Adapter
 * @receiver RecyclerView.Adapter<*>
 * @see RecyclerView.Adapter.notifyDataSetChanged
 */
fun RecyclerView.Adapter<*>.notifyDataChanged() {
    notifyDataSetChanged()
}