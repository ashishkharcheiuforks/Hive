package com.gpetuhov.android.hive.ui.epoxy.review.models

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.ui.epoxy.base.KotlinHolder
import com.gpetuhov.android.hive.util.setVisible
import com.gpetuhov.android.hive.util.updateUserPic

@EpoxyModelClass(layout = R.layout.review_item_view)
abstract class ReviewItemModel : EpoxyModelWithHolder<ReviewItemHolder>() {

    @EpoxyAttribute lateinit var userPicUrl: String
    @EpoxyAttribute lateinit var username: String

    @EpoxyAttribute lateinit var time: String

    @EpoxyAttribute lateinit var reviewText: String

    @EpoxyAttribute var rating = 0.0F
    @EpoxyAttribute var ratingVisible = true

    @EpoxyAttribute var controlsVisible = false
    @EpoxyAttribute lateinit var onEditClick: () -> Unit
    @EpoxyAttribute lateinit var onDeleteClick: () -> Unit

    @EpoxyAttribute var commentVisible = false
    @EpoxyAttribute lateinit var onCommentClick: () -> Unit

    @EpoxyAttribute var commentTextVisible = false
    @EpoxyAttribute lateinit var commentText: String

    @EpoxyAttribute var commentControlsVisible = false
    @EpoxyAttribute lateinit var onCommentEditClick: () -> Unit
    @EpoxyAttribute lateinit var onCommentDeleteClick: () -> Unit

    @EpoxyAttribute var showOfferVisible = false
    @EpoxyAttribute lateinit var onShowOfferClick: () -> Unit

    override fun bind(holder: ReviewItemHolder) {
        updateUserPic(holder.userPic.context, userPicUrl, holder.userPic)
        holder.username.text = username
        holder.reviewText.text = reviewText
        holder.time.text = time

        holder.ratingBar.rating = rating
        holder.ratingBar.setVisible(ratingVisible)

        holder.separator.setVisible(!(controlsVisible || commentVisible || showOfferVisible) && ratingVisible)

        holder.controlsWrapper.setVisible(controlsVisible)
        holder.edit.setOnClickListener { onEditClick() }
        holder.delete.setOnClickListener { onDeleteClick() }

        holder.comment.setVisible(commentVisible)
        holder.comment.setOnClickListener { onCommentClick() }

        holder.commentTextWrapper.setVisible(commentTextVisible)
        holder.commentText.text = commentText

        holder.commentControlsWrapper.setVisible(commentControlsVisible)
        holder.commentEdit.setOnClickListener { onCommentEditClick() }
        holder.commentDelete.setOnClickListener { onCommentDeleteClick() }

        holder.showOffer.setVisible(showOfferVisible)
        holder.showOffer.setOnClickListener { onShowOfferClick() }
    }
}

class ReviewItemHolder : KotlinHolder() {
    val userPic by bind<ImageView>(R.id.review_item_user_pic)
    val username by bind<TextView>(R.id.review_item_user_name)
    val time by bind<TextView>(R.id.review_item_time)
    val reviewText by bind<TextView>(R.id.review_item_text)
    val ratingBar by bind<AppCompatRatingBar>(R.id.review_item_rating_bar)
    val separator by bind<View>(R.id.review_item_separator)
    val controlsWrapper by bind<View>(R.id.review_item_controls_wrapper)
    val edit by bind<TextView>(R.id.review_item_edit)
    val delete by bind<TextView>(R.id.review_item_delete)
    val comment by bind<TextView>(R.id.review_item_comment)
    val commentTextWrapper by bind<View>(R.id.review_item_comment_text_wrapper)
    val commentText by bind<TextView>(R.id.review_item_comment_text)
    val commentControlsWrapper by bind<View>(R.id.review_item_comment_controls_wrapper)
    val commentEdit by bind<TextView>(R.id.review_item_comment_edit)
    val commentDelete by bind<TextView>(R.id.review_item_comment_delete)
    val showOffer by bind<TextView>(R.id.review_item_show_offer)
}