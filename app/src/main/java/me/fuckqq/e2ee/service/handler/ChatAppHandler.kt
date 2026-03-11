package me.fuckqq.e2ee.service.handler

import android.view.accessibility.AccessibilityEvent
import com.dianming.phoneapp.MyAccessibilityService


/**
 * 聊天应用处理器的通用接口。
 * 定义了所有受支持的聊天应用都需要提供的基本信息和逻辑。
 */
interface ChatAppHandler {
    /**
     * 该处理器对应的应用包名。
     */
    val packageName: String

    /**
     * 聊天界面输入框的资源 ID。
     */
    val inputId: String

    /**
     * 聊天界面发送按钮的资源 ID。
     */
    val sendBtnId: String

    /**
     * 气泡消息的 ID
     */
    val messageTextId: String

    /**
     * 存放消息列表的 className，QQ 的这个 class 无 ID，则不提供
     */
    val messageListClassName: String

    /**
     * 获取当前聊天对象的名称（联系人或群名）。
     * @return 当前聊天对象的名称，如果无法获取则返回 null。
     */
    fun getCurrentChatPartnerName(): String?

    /**
     * 当该处理器被激活时调用（例如，用户打开了对应的 App）。
     * @param service 无障碍服务的实例，用于获取上下文、协程作用域等。
     */
    fun onHandlerActivated(service: MyAccessibilityService)

    /**
     * 当该处理器被停用时调用（例如，用户离开了对应的 App）。
     */
    fun onHandlerDeactivated()

    /**
     * 处理该应用相关的无障碍事件。
     * @param event 接收到的事件。
     * @param service 无障碍服务的实例。
     */
    fun onAccessibilityEvent(event: AccessibilityEvent, service: MyAccessibilityService)
}
