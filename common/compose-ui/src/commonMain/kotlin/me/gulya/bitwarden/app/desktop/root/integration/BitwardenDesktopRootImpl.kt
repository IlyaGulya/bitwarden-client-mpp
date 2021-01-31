package me.gulya.bitwarden.app.desktop.root.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.router
import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import com.arkivanov.decompose.value.Value
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.common.utils.Consumer
import me.gulya.bitwarden.app.desktop.root.BitwardenDesktopRoot
import me.gulya.bitwarden.app.desktop.root.BitwardenDesktopRoot.Child
import me.gulya.bitwarden.app.desktop.root.BitwardenDesktopRoot.Dependencies

internal class BitwardenDesktopRootImpl(
    componentContext: ComponentContext,
    dependencies: Dependencies
) : BitwardenDesktopRoot, ComponentContext by componentContext, Dependencies by dependencies {

    private val router =
        router<Configuration, Child>(
            initialConfiguration = Configuration.Main,
            handleBackButton = true,
            componentFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Login -> Child.Login(login(componentContext))
            is Configuration.Main -> Child.Main(main(componentContext))
        }

    private fun login(componentContext: ComponentContext): BitwardenLogin =
        BitwardenLogin(
            componentContext = componentContext,
            dependencies = object : BitwardenLogin.Dependencies, Dependencies by this {
                override val loginOutput: Consumer<BitwardenLogin.Output> = Consumer(::onLoginOutput)
            }
        )

    private fun main(componentContext: ComponentContext): BitwardenDesktopMain =
        BitwardenDesktopMain(
            componentContext = componentContext,
            dependencies = object : BitwardenDesktopMain.Dependencies, Dependencies by this {}
        )

    private fun onLoginOutput(output: BitwardenLogin.Output): Unit =
        when (output) {
            is BitwardenLogin.Output.Finished -> router.push(Configuration.Main)
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Login : Configuration()
    }
}
