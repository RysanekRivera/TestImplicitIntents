package com.rysanek.testimplicitintents.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.testimplicitintents.databinding.SingleIntroBinding
import com.rysanek.testimplicitintents.models.RvItem
import com.rysanek.testimplicitintents.ui.fragments.BaseFragment
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class IntroAdapter(
    private var onItemClick: KClass<out BaseFragment>.() -> Unit
): RecyclerView.Adapter<IntroAdapter.IntroVieHolder>() {
    
    var listOfItems = mutableListOf<RvItem>()
    
    class IntroVieHolder(private val binding: SingleIntroBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): IntroVieHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = SingleIntroBinding.inflate(inflater)
                return IntroVieHolder(binding)
            }
        }
        
        fun bind(rvItem: RvItem){
            binding.ivItemIcon.setImageDrawable(ContextCompat.getDrawable(binding.root.context, rvItem.iconId))
            binding.tvItemAction.text = rvItem.itemActionText
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroVieHolder {
        return IntroVieHolder.from(parent)
    }
    
    override fun onBindViewHolder(holder: IntroVieHolder, position: Int) {
        val currentItem = listOfItems[position]
        
        holder.bind(currentItem)
        
        holder.itemView.setOnClickListener {
            onItemClick(currentItem.fragment)
        }
    }
    
    override fun getItemCount() = listOfItems.size
    
    fun setData(itemsList: MutableList<RvItem>) {
        listOfItems = itemsList
    }
}