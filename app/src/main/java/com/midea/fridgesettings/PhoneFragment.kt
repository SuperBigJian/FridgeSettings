package com.midea.fridgesettings

/**
 * Created by chenjian on 6/1/17.
 */
class PhoneFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_PHONE) : BaseFragment(type) {
    override fun getContentViewLayoutId(): Int = R.layout.fragment_phone

    override fun initViewsAndEvents() {
        super.initViewsAndEvents()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()
    }
}