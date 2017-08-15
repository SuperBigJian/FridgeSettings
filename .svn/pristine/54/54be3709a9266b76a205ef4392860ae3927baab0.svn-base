package com.midea.fridgesettings

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_sound.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textResource


/**
 * Created by chenjian on 6/1/17.
 */
class SoundFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_SOUND) : BaseFragment(type) {

    private val audioMgr by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private val mediaPlayer by lazy { MediaPlayer.create(context, R.raw.beep) }
    private var currentVolume: Int = 0
    private var maxVolume: Int = 100
    override fun getContentViewLayoutId(): Int = R.layout.fragment_sound

    override fun initViewsAndEvents() {

        with(pb_sound) {
            title.textResource = R.string.volume
            image.imageResource = R.mipmap.ic_mute
            maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            seek.max = maxVolume
            seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                    currentVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC)
                    seek.progress = currentVolume
                    value.text = (currentVolume * 100 / maxVolume).toString()
                    if (currentVolume == 0) {
                        pb_sound.image.visibility = View.VISIBLE
                        pb_sound.value.visibility = View.GONE
                    } else {
                        pb_sound.image.visibility = View.GONE
                        pb_sound.value.visibility = View.VISIBLE
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({ mediaPlayer.start() }, 500)
                }
            })
        }


    }

    override fun onUserVisible() {
        super.onUserVisible()
        initVolume()
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()
        mediaPlayer.release()
    }

    private fun initVolume() {
        with(pb_sound) {
            currentVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC)
            seek.progress = currentVolume
            value.text = (currentVolume * 100 / maxVolume).toString()
            if (currentVolume == 0) {
                image.visibility = View.VISIBLE
                value.visibility = View.GONE
            } else {
                image.visibility = View.GONE
                value.visibility = View.VISIBLE
            }
        }
    }
}