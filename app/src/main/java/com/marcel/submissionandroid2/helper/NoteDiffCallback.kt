package com.marcel.submissionandroid2.helper

import androidx.recyclerview.widget.DiffUtil
import com.marcel.submissionandroid2.database.User

class NoteDiffCallback(private val mOldUserList: List<User>,private val mNewUserList: List<User>):
    DiffUtil.Callback() {

        override fun getOldListSize(): Int{
            return mOldUserList.size
        }

        override fun getNewListSize(): Int {
            return mNewUserList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
        }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUserList[oldItemPosition]
        val newUser = mNewUserList[newItemPosition]
        return oldUser.login == newUser.login && oldUser.avatar_url == newUser.avatar_url
    }
}