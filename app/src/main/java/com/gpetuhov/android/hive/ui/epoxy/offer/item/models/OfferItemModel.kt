package com.gpetuhov.android.hive.ui.epoxy.offer.item.models

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.ui.epoxy.base.KotlinHolder
import com.gpetuhov.android.hive.util.getPriceColorId
import com.gpetuhov.android.hive.util.getStarResourceId
import com.gpetuhov.android.hive.util.setVisible

@EpoxyModelClass(layout = R.layout.offer_item_view)
abstract class OfferItemModel : EpoxyModelWithHolder<OfferItemHolder>() {

    @EpoxyAttribute var active = false
    @EpoxyAttribute var activeVisible = false

    @EpoxyAttribute lateinit var title: String

    @EpoxyAttribute var free = true
    @EpoxyAttribute lateinit var price: String

    @EpoxyAttribute lateinit var onClick: () -> Unit

    @EpoxyAttribute var favorite = false
    @EpoxyAttribute lateinit var onFavoriteButtonClick: () -> Unit

    @EpoxyAttribute lateinit var offerStarCount: String

    @EpoxyAttribute var rating = 0.0F
    @EpoxyAttribute var reviewCount = 0

    @EpoxyAttribute lateinit var distance: String
    @EpoxyAttribute var distanceVisible = false

    override fun bind(holder: OfferItemHolder) {
        holder.active.setImageResource(if (active) R.drawable.circle_green else R.drawable.circle_red)
        holder.active.setVisible(activeVisible)

        holder.title.text = title

        holder.price.text = price

        holder.price.setTextColor(ContextCompat.getColor(holder.price.context, getPriceColorId(free)))

        holder.favoriteButton.setImageResource(getStarResourceId(favorite))
        holder.favoriteButton.setOnClickListener { onFavoriteButtonClick() }

        holder.offerStarCount.text = offerStarCount

        val ratingVisible = reviewCount != 0
        holder.ratingWrapper.setVisible(ratingVisible)
        holder.ratingBar.rating = rating
        holder.reviewCount.text = reviewCount.toString()

        holder.distance.text = distance
        holder.distance.setVisible(distanceVisible)

        holder.rootView.setOnClickListener { onClick() }
    }
}

class OfferItemHolder : KotlinHolder() {
    val rootView by bind<View>(R.id.offer_item_root)
    val active by bind<ImageView>(R.id.offer_item_active)
    val title by bind<TextView>(R.id.offer_item_title)
    val price by bind<TextView>(R.id.offer_item_price)
    val favoriteButton by bind<ImageView>(R.id.offer_item_favorite_button)
    val ratingWrapper by bind<View>(R.id.offer_item_rating_wrapper)
    val ratingBar by bind<AppCompatRatingBar>(R.id.offer_item_rating_bar)
    val reviewCount by bind<TextView>(R.id.offer_item_review_count)
    val offerStarCount by bind<TextView>(R.id.offer_item_star_count)
    val distance by bind<TextView>(R.id.offer_item_distance)
}