package com.example.t_shopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class tending_Adapter constructor(var context: Context, var list: ArrayList<tending_gs?>) :
    RecyclerView.Adapter<tending_Adapter.MyviewHolder>() {
    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.trending, parent, false)
        return MyviewHolder(view)
    }

    public override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val tending_gs: tending_gs? = list.get(position)
        holder.Name.setText(tending_gs?.name)
        Glide.with(holder.Productimg.getContext()).load(tending_gs?.image).into(holder.Productimg)
    }

    public override fun getItemCount(): Int {
        return list.size
    }

    class MyviewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var Name: TextView
        var Size: TextView? = null
        var Price: TextView? = null
        var Buy: AppCompatButton
        var Productimg: CircleImageView

        init {
            Name = itemView.findViewById(R.id.trending_item_name)
            Productimg = itemView.findViewById(R.id.tren_img)
            Buy = itemView.findViewById(R.id.buy)
            Buy.setOnClickListener(object : View.OnClickListener {
                public override fun onClick(v: View) {
                    val buyintent: Intent = Intent(itemView.getContext(), Home::class.java)
                    itemView.getContext().startActivity(buyintent)
                }
            })
            itemView.setOnClickListener(this)
        }

        public override fun onClick(v: View) {
            val intent: Intent = Intent(itemView.getContext(), Product_about::class.java)
            itemView.getContext().startActivity(intent)
        }
    }
}