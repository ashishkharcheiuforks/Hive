package com.gpetuhov.android.hive.ui.epoxy.offer.details.models

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.ui.epoxy.base.KotlinHolder
import com.gpetuhov.android.hive.util.getPriceColorId

@EpoxyModelClass(layout = R.layout.offer_details_details_view)
abstract class OfferDetailsDetailsModel : EpoxyModelWithHolder<OfferDetailsDetailsHolder>() {

    @EpoxyAttribute lateinit var description: String
    @EpoxyAttribute var free = true
    @EpoxyAttribute lateinit var price: String

    override fun bind(holder: OfferDetailsDetailsHolder) {
        holder.description.text = description
        holder.price.text = price

        holder.price.setTextColor(ContextCompat.getColor(holder.price.context, getPriceColorId(free)))
    }
}

class OfferDetailsDetailsHolder : KotlinHolder() {
    val description by bind<TextView>(R.id.offer_details_description)
    val price by bind<TextView>(R.id.offer_details_price)
}