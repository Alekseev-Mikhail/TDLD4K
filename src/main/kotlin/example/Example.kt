package example

import tdld4k.SingleClient
import tdld4k.controllers.MoveMouseWithRobotInput
import tdld4k.math.Vector2Double
import tdld4k.world.AABBTile
import tdld4k.world.FullTile
import tdld4k.world.World
import java.awt.Color
import java.awt.Color.BLUE
import java.awt.Color.DARK_GRAY
import java.awt.Color.GRAY
import java.awt.Color.ORANGE
import java.awt.Color.YELLOW
import java.awt.Font.PLAIN
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.KeyEvent.VK_A
import java.awt.event.KeyEvent.VK_D
import java.awt.event.KeyEvent.VK_S
import java.awt.event.KeyEvent.VK_W

fun main() {
    val map = "11111111111111111" +
        "1   4  1        1" +
        "1   4    1 111111" +
        "1   4 3  1      1" +
        "1    11111111   1" +
        "1          1    1" +
        "1111 1111  1  111" +
        "1          1  1 1" +
        "1   111    1  1 1" +
        "1               1" +
        "111111     1  111" +
        "1  1       1  1 1" +
        "1     111111    1" +
        "1  1            1" +
        "1  11  1111   111" +
        "1               1" +
        "11111111111111111"
    val mapWidth = 17
    val tileSize = 5

    val tileTypes = mapOf(
        Pair('1', FullTile(GRAY)),
        Pair('2', FullTile(DARK_GRAY)),
        Pair('3', AABBTile(YELLOW, Vector2Double(1.0, 1.0), Vector2Double(4.0, 4.0))),
        Pair('4', AABBTile(ORANGE, Vector2Double(0.0, 0.0), Vector2Double(0.1, 5.0))),
    )
    val world = World(map, mapWidth, tileSize, ' ', FullTile(BLUE), tileTypes)
    val player = ExamplePlayer(
        10.0,
        10.0,
        0.0,
        70.0,
        10.0,
        400.0,
        6.0,
        0.4,
        60,
        isFreezeMovement = false,
        isFreezeRotation = false,
        isShowDebugMenu = false,
    )
    val cameraLayers = CameraLayers(
        player,
        25,
        PLAIN,
        Point(5, 5),
        8,
        Color(82, 82, 82, 190),
        Color(240, 240, 240, 220),
    )
    val singleClient = SingleClient(world, cameraLayers, player)
    val moveMouseWithRobotInput = MoveMouseWithRobotInput(isRobot = false, isFreezeMove = false)
    val keyboardController = KeyboardController(
        world,
        VK_W,
        VK_S,
        VK_A,
        VK_D,
        singleClient,
        player,
        true,
        moveMouseWithRobotInput,
    )
    val mouseController = MouseController(singleClient, player, moveMouseWithRobotInput)
    val firstCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        SingleClient.getImage("/example/cursor.png"),
        Point(0, 0),
        "first custom cursor",
    )
    val secondCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        SingleClient.getImage("/example/cursor1.png"),
        Point(0, 0),
        "second custom cursor",
    )

    mouseController.firstCustomCursor = firstCustomCursor
    mouseController.secondCustomCursor = secondCustomCursor
    singleClient.setCurrentCursor(firstCustomCursor)

    singleClient.initializationFrame(keyboardController, mouseController)
    singleClient.setFullscreenMode()

    singleClient.setInvisibleCursor()
    singleClient.playerFrame.title = "Example"
    singleClient.playerFrame.iconImage = SingleClient.getImage("/example/icon.jpg")
    singleClient.playerFrame.isVisible = true
    singleClient.changeFrameSize(512, 512)

    cameraLayers.addDebugObject(player)
    singleClient.startFpsCounter()
}
