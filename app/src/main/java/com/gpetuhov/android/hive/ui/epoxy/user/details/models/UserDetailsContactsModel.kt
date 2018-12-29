package com.gpetuhov.android.hive.ui.epoxy.user.details.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.ui.epoxy.base.KotlinHolder

@EpoxyModelClass(layout = R.layout.user_details_contacts_view)
abstract class UserDetailsContactsModel : EpoxyModelWithHolder<UserDetailsContactsHolder>() {

    @EpoxyAttribute lateinit var phone: String
    @EpoxyAttribute lateinit var onPhoneClick: () -> Unit

    @EpoxyAttribute lateinit var email: String
    @EpoxyAttribute lateinit var onEmailClick: () -> Unit

    override fun bind(holder: UserDetailsContactsHolder) {
        holder.phone.text = phone
        holder.phoneWrapper.setOnClickListener { onPhoneClick() }

        holder.email.text = email
        holder.emailWrapper.setOnClickListener { onEmailClick() }
    }
}

class UserDetailsContactsHolder : KotlinHolder() {
    val phoneWrapper by bind<View>(R.id.user_details_phone_wrapper)
    val phone by bind<TextView>(R.id.user_details_phone)
    val emailWrapper by bind<View>(R.id.user_details_visible_email_wrapper)
    val email by bind<TextView>(R.id.user_details_visible_email)
}