package com.example.t_shopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class Face_adapter constructor(var context: Context, var list: ArrayList<Face_Gs?>) :
    RecyclerView.Adapter<Face_adapter.MyviewHolder>() {
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.rv_custom, parent, false)
        return MyviewHolder(view)
    }

    public override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val face_gs: Face_Gs? = list.get(position)
        holder.Name.setText(face_gs?.name)
        holder.Price.setText(face_gs?.price)
        holder.Size.setText(face_gs?.size)
        Glide.with(holder.Productimg.getContext()).load(face_gs?.image).into(holder.Productimg)
    }

    public override fun getItemCount(): Int {
        return list.size
    }

    class MyviewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var Name: TextView
        var Size: TextView
        var Price: TextView
        var Productimg: CircleImageView

        init {
            Name = itemView.findViewById(R.id.iproduct_name)
            Size = itemView.findViewById(R.id.iproduct_size)
            Price = itemView.findViewById(R.id.iproduct_price)
            Productimg = itemView.findViewById(R.id.product_img)
            itemView.setOnClickListener(this)
        }

        public override fun onClick(v: View) {
            val intent: Intent = Intent(itemView.getContext(), Product_about::class.java)
            itemView.getContext().startActivity(intent)
        }
    }
}