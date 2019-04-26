package com.gpetuhov.android.hive.ui.epoxy.user.details.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.ui.epoxy.base.KotlinHolder
import com.gpetuhov.android.hive.util.setVisible

@EpoxyModelClass(layout = R.layout.user_details_contacts_view)
abstract class UserDetailsContactsModel : EpoxyModelWithHolder<UserDetailsContactsHolder>() {

    @EpoxyAttribute lateinit var phone: String
    @EpoxyAttribute lateinit var onPhoneClick: () -> Unit
    @EpoxyAttribute var phoneVisible = true

    @EpoxyAttribute lateinit var email: String
    @EpoxyAttribute lateinit var onEmailClick: () -> Unit
    @EpoxyAttribute var emailVisible = true
    @EpoxyAttribute var emailSeparatorVisible = true

    @EpoxyAttribute lateinit var skype: String
    @EpoxyAttribute lateinit var onSkypeClick: () -> Unit
    @EpoxyAttribute var skypeVisible = true
    @EpoxyAttribute var skypeSeparatorVisible = true

    @EpoxyAttribute lateinit var facebook: String
    @EpoxyAttribute lateinit var onFacebookClick: () -> Unit
    @EpoxyAttribute var facebookVisible = true
    @EpoxyAttribute var facebookSeparatorVisible = true

    override fun bind(holder: UserDetailsContactsHolder) {
        holder.phone.text = phone
        holder.phoneWrapper.setVisible(phoneVisible)
        holder.phoneWrapper.setOnClickListener { onPhoneClick() }

        holder.email.text = email
        holder.emailWrapper.setVisible(emailVisible)
        holder.emailSeparator.setVisible(emailSeparatorVisible)
        holder.emailWrapper.setOnClickListener { onEmailClick() }

        holder.skype.text = skype
        holder.skypeWrapper.setVisible(skypeVisible)
        holder.skypeSeparator.setVisible(skypeSeparatorVisible)
        holder.skypeWrapper.setOnClickListener { onSkypeClick() }

        holder.facebook.text = facebook
        holder.facebookWrapper.setVisible(facebookVisible)
        holder.facebookSeparator.setVisible(facebookSeparatorVisible)
        holder.facebookWrapper.setOnClickListener { onFacebookClick() }
    }
}

class UserDetailsContactsHolder : KotlinHolder() {
    val phoneWrapper by bind<View>(R.id.user_details_phone_wrapper)
    val phone by bind<TextView>(R.id.user_details_phone)
    val emailWrapper by bind<View>(R.id.user_details_visible_email_wrapper)
    val email by bind<TextView>(R.id.user_details_visible_email)
    val emailSeparator by bind<View>(R.id.user_details_email_separator)
    val skypeWrapper by bind<View>(R.id.user_details_skype_wrapper)
    val skype by bind<TextView>(R.id.user_details_skype)
    val skypeSeparator by bind<View>(R.id.user_details_skype_separator)
    val facebookWrapper by bind<View>(R.id.user_details_facebook_wrapper)
    val facebook by bind<TextView>(R.id.user_details_facebook)
    val facebookSeparator by bind<View>(R.id.user_details_facebook_separator)
}