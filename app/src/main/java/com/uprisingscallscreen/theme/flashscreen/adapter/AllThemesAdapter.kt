package com.uprisingscallscreen.theme.flashscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uprisingscallscreen.theme.flashscreen.databinding.AllthemesItemLayoutBinding
import com.uprisingscallscreen.theme.flashscreen.keyboardutils.isVisible
import com.uprisingscallscreen.theme.flashscreen.models.ThemesModel
import com.bumptech.glide.Glide

class AllThemesAdapter(
    private val onThemeClicked: (ThemesModel) -> Unit
) : ListAdapter<ThemesModel, RecyclerView.ViewHolder>(ItemDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            AllthemesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onThemeClicked
        )
    }

    override fun getItemViewType(position: Int): Int {

        return if (currentList[position].isNativeAd) 1
        else
            0
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (holder) {
            is ItemViewHolder -> {
                holder.bind(getItem(position))
                val layoutParams =
                    holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = false


            }
        }

    }


    inner class ItemViewHolder(
        private val themeItemBinding: AllthemesItemLayoutBinding,
        private val onThemeClicked: (ThemesModel) -> Unit
    ) : RecyclerView.ViewHolder(themeItemBinding.root) {

        private var currentTheme: ThemesModel? = null


        init {


            themeItemBinding.root.setOnClickListener {


                currentTheme?.let {

                    onThemeClicked.invoke(it)

                }

            }

        }

        fun bind(item: ThemesModel) {
            this.currentTheme = item
            currentTheme?.let {

                if (!it.isNeon!!) {
                    if (it.themeId == 1)
                        themeItemBinding.themeIvLayer2.isVisible(false)
                    else
                        themeItemBinding.themeIvLayer2.isVisible(true)
                    setUpImages(it.themeDrawableName!!)
                    setUpNeonKeysImages(it.keysBgName!!)

                } else {
                    themeItemBinding.themeIvLayer2.isVisible(true)
                    setUpImages(it.themeDrawableName!!)
                    setUpNeonKeysImages(it.keysBgName!!)
                }



                bindThemeState(it.themeIsSelected)

            }

        }


        private fun setUpImages(drawableName: String) {

            val drawableId = themeItemBinding.root.context.resources.getIdentifier(
                drawableName,
                "drawable",
                themeItemBinding.root.context.packageName
            )

            Glide.with(themeItemBinding.root)
                .load(drawableId)
                .into(themeItemBinding.themeIv)

        }


        private fun setUpNeonKeysImages(drawableNameKeys: String) {
            val drawableId = themeItemBinding.root.context.resources.getIdentifier(
                drawableNameKeys,
                "drawable",
                themeItemBinding.root.context.packageName
            )

            Glide.with(themeItemBinding.root)
                .load(drawableId)
                .into(themeItemBinding.themeIvLayer2)
        }

        private fun bindThemeState(isThemeSelected: Boolean) {
            themeItemBinding.themeSelectedIv.isVisible(isThemeSelected)
            if (isThemeSelected) {
                themeItemBinding.themeIv.alpha = 0.5F
            } else {
                themeItemBinding.themeIv.alpha = 1.0F
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return currentList[position].themeId.toLong()
    }


}


class ItemDiffCallback : DiffUtil.ItemCallback<ThemesModel>() {
    override fun areItemsTheSame(oldItem: ThemesModel, newItem: ThemesModel): Boolean {
        return oldItem.themeId == newItem.themeId
    }

    override fun areContentsTheSame(oldItem: ThemesModel, newItem: ThemesModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ThemesModel, newItem: ThemesModel): Any {
        return oldItem.themeIsSelected != newItem.themeIsSelected
    }
}



