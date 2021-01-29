package me.gulya.bitwarden.app.desktop.ui.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.router
import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import com.arkivanov.decompose.value.Value
import com.badoo.reaktive.base.Consumer
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.common.utils.Consumer
import me.gulya.bitwarden.app.desktop.ui.BitwardenDesktopContentRoot
import me.gulya.bitwarden.app.desktop.ui.BitwardenDesktopContentRoot.Child
import me.gulya.bitwarden.app.desktop.ui.BitwardenDesktopContentRoot.Dependencies
import me.gulya.bitwarden.domain.login.LoginInteractor

internal class BitwardenDesktopContentRootImpl(
    componentContext: ComponentContext,
    dependencies: Dependencies
) : BitwardenDesktopContentRoot, ComponentContext by componentContext, Dependencies by dependencies {

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
            is Configuration.Main -> Child.Login(login(componentContext))
//            is Configuration.Edit -> Child.Edit(todoEdit(componentContext, itemId = configuration.itemId))
        }

    private fun login(componentContext: ComponentContext): BitwardenLogin =
        BitwardenLogin(
            componentContext = componentContext,
            dependencies = object : BitwardenLogin.Dependencies, Dependencies by this {
                override val loginInteractorFactory: (serverUrl) -> LoginInteractor
                    get() = TODO("Not yet implemented")
                override val loginOutput: Consumer<BitwardenLogin.Output> = Consumer(::onLoginOutput)
            }
        )

//    private fun todoEdit(componentContext: ComponentContext, itemId: Long): TodoEdit =
//        TodoEdit(
//            componentContext = componentContext,
//            dependencies = object : TodoEdit.Dependencies, Dependencies by this {
//                override val itemId: Long = itemId
//                override val editOutput: Consumer<TodoEdit.Output> = Consumer(::onEditOutput)
//            }
//        )

    private fun onLoginOutput(output: BitwardenLogin.Output): Unit =
        when (output) {
            is BitwardenLogin.Output.Finished -> router.push(Configuration.Edit(itemId = output.id))
        }

    private fun onEditOutput(output: TodoEdit.Output): Unit =
        when (output) {
            is TodoEdit.Output.Finished -> router.pop()
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Login : Configuration()
    }
}
