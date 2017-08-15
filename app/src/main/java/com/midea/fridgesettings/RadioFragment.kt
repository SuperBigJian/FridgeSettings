package com.midea.fridgesettings

import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_radio.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textResource


/**
 * Created by chenjian on 6/1/17.
 */
class RadioFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_RADIO) : BaseFragment(type) {


    override fun getContentViewLayoutId(): Int = R.layout.fragment_radio

    override fun initViewsAndEvents() {

        with(radioOpen) {
            tvTitle.textSize = 30f
            tvTitle.textColor = Color.rgb(103, 107, 108)
            tvTitle.textResource = R.string.open_radio
            switch.isChecked = true
            switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    exLayout.expand()
                } else {
                    exLayout.moveChild(0)
                }
            }
        }

        with(weatherOpen) {
            tvTitle.textSize = 30f
            tvTitle.textColor = Color.rgb(103, 107, 108)
            tvTitle.textResource = R.string.open_weather
            switch.isChecked = true
            switch.setOnCheckedChangeListener { _, isChecked ->

            }
        }

        from_hour.data = (5..7).map { String.format("%02d", it) }
        from_minute.data = (0..59).map { String.format("%02d", it) }

        to_hour.data = (10..11).map { String.format("%02d", it) }
        to_minute.data = (0..59).map { String.format("%02d", it) }

        from_hour.selectedItemPosition = 6 - 5
        from_minute.selectedItemPosition = 0

        to_hour.selectedItemPosition = 11 - 10
        to_minute.selectedItemPosition = 59
    }

    override fun onUserVisible() {
        super.onUserVisible()
        exLayout.post {
            if (radioOpen.switch.isChecked) {
                exLayout.expand()
            } else {
                exLayout.moveChild(0)
            }
        }
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()

    }
}