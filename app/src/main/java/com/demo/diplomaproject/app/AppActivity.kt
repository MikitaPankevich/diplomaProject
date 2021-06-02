package com.demo.diplomaproject.app

import android.os.Bundle
import com.demo.diplomaproject.R
import com.demo.diplomaproject.app.presentation.AppPresenter
import com.demo.diplomaproject.app.presentation.AppView
import com.demo.diplomaproject.core.AnimatedSupportAppNavigator
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import kotlinx.android.synthetic.main.activity_app.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity : MvpAppCompatActivity(), AppView {

    private val navigator: Navigator =
        AnimatedSupportAppNavigator(this, supportFragmentManager, R.id.appContainer)

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.appContainer) as? BaseFragment

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: AppPresenter

    @ProvidePresenter
    fun providePresenter(): AppPresenter =
        Toothpick.openScope(DI.SERVER_SCOPE).getInstance(AppPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(DI.SERVER_SCOPE))

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)

        if (savedInstanceState == null) {
            appContainer.postDelayed(
                {
                    presenter.onAppStarted()
                    window.setBackgroundDrawableResource(R.color.white)
                },
                SHOW_LOGO_DELAY
            )
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()

        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        appContainer.handler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: presenter.onBackPressed()
    }

    companion object {

        private const val SHOW_LOGO_DELAY = 1000L
    }
}
