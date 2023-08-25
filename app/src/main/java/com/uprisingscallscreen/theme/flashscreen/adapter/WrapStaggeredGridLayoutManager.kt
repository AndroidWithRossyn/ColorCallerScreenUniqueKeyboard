package com.uprisingscallscreen.theme.flashscreen.adapter

import androidx.recyclerview.widget.StaggeredGridLayoutManager

class WrapStaggeredGridLayoutManager(spanCount: Int, orientation: Int) :
    StaggeredGridLayoutManager(spanCount, orientation) {

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}