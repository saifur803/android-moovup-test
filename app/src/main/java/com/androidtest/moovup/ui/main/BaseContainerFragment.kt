package com.androidtest.moovup.ui.main

import androidx.fragment.app.Fragment
import com.androidtest.moovup.R

abstract class BaseContainerFragment: Fragment() {

    fun isFragmentActive(tag: String?): Boolean {
        val fragment = childFragmentManager.findFragmentByTag(tag) ?: return false
        return fragment.isVisible
    }

    fun addFragment(
        fragment: Fragment,
        tag: String,
        layout: Int = R.id.frameContainer,
        isBackStack: Boolean = false
    ) {
        val fragmentManager = childFragmentManager
        val transaction = childFragmentManager.beginTransaction()
        //hide active
        fragmentManager.fragments.firstOrNull { it.isVisible }?.let {
            transaction.hide(it)
        }
        transaction.add(layout, fragment, tag)
        if (isBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    fun showFragment(
        tag: String,
        layout: Int = R.id.frameContainer
    ): Boolean {
        val fragmentManager = childFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)?: return false

        val transaction = childFragmentManager.beginTransaction()
        //hide active
        fragmentManager.fragments.firstOrNull { it.isVisible }?.let {
            transaction.hide(it)
        }

        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(layout, fragment, tag)
        }
        transaction.commit()
        return true
    }
}