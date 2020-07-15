/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.createImageView
import com.microsoft.fluentui.view.TemplateView

class SheetHorizontalItemView: TemplateView {
    lateinit var sheetItemViewContainer: ViewGroup
    lateinit var sheetItemTitle:TextView
    lateinit var mainContainer:View

    var onSheetItemClickListener: ListItem.OnClickListener? = null
    var title: String = ""
    var customView: View? = null
    var listItem: ListItem? = null

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_view

    @JvmOverloads
    constructor(context: Context,  attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    constructor(context: Context, listItem: ListItem,  attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        this.listItem = listItem
        this.title = listItem.title
        this.customView = context.createImageView(listItem.drawable)
    }

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        sheetItemViewContainer = findViewInTemplateById(R.id.sheet_item_view_container)!!
        sheetItemTitle = findViewInTemplateById(R.id.sheet_item_title)!!
        mainContainer = findViewInTemplateById(R.id.main_container)!!
        updateTitleView()
        updateCustomView()

        if(listItem != null)
            mainContainer.setOnClickListener {
                onSheetItemClickListener?.onSheetItemClick(listItem!!)
            }
        mainContainer.setBackgroundResource(R.drawable.bottom_sheet_item_ripple_background)
    }

    fun updateTitleView() {
        sheetItemTitle.text = title
        sheetItemTitle.visibility = if (title.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun updateCustomView() {
        sheetItemViewContainer.removeAllViews()
        if(customView != null)
            sheetItemViewContainer.addView(customView)
    }

    fun update(title:String, customView:View) {
        this.title = title
        this.customView = customView
        updateTitleView()
        updateCustomView()
    }
}