package example

import tdld4k.SingleClient
import tdld4k.controllers.MoveMouseWithRobotInput
import tdld4k.debug.DebugObject
import tdld4k.math.Vector2
import tdld4k.player.PlayerHeightLimits.MID_HEIGHT
import tdld4k.world.AABBTile
import tdld4k.world.AirTile
import tdld4k.world.FullTile
import tdld4k.world.World
import java.awt.Color
import java.awt.Color.BLUE
import java.awt.Color.DARK_GRAY
import java.awt.Color.GRAY
import java.awt.Color.ORANGE
import java.awt.Color.YELLOW
import java.awt.Font
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
        "1 1 4    1 111111" +
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
    val tileSize = 3.5
    val tileTypes = mapOf(
        Pair(' ', AirTile()),
        Pair('1', FullTile(GRAY, tileSize)),
        Pair('2', FullTile(DARK_GRAY, tileSize)),
        Pair('3', AABBTile(YELLOW, Vector2(1.0, 1.0), Vector2(2.5, 2.5))),
        Pair('4', AABBTile(ORANGE, Vector2(0.0, 0.0), Vector2(0.1, tileSize))),
    )
    val world = World(map, mapWidth, tileSize, FullTile(BLUE, tileSize), tileTypes)
    val player = ExamplePlayer(
        6.0,
        MID_HEIGHT.value,
        6.0,
        0.0,
        1.0,
        70.0,
        10.0,
        400.0,
        0.05,
        0.2,
        0.001,
        60,
        isFreezeMovement = false,
        isFreezeRotation = false,
    )
    val singleClient = SingleClient(world, player)
    val cameraLayers = Menus(
        singleClient,
        player,
        Color(82, 82, 82, 190),
        8,
        Font("debug menu", PLAIN, 25),
        Color(240, 240, 240, 220),
        Point(5, 5),
    )
    val moveMouseWithRobotInput = MoveMouseWithRobotInput(isRobot = false, isFreezeMove = false)
    val keyboardController = KeyboardController(
        world,
        VK_W,
        VK_S,
        VK_A,
        VK_D,
        singleClient,
        player,
        cameraLayers,
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
    val autoEscape = AutoEscape(keyboardController)

    mouseController.firstCustomCursor = firstCustomCursor
    mouseController.secondCustomCursor = secondCustomCursor
    singleClient.setCurrentCursor(firstCustomCursor)

    singleClient.initializationFrame(keyboardController, mouseController, autoEscape)

    singleClient.setInvisibleCursor()
    singleClient.playerFrame.title = "Example"
    singleClient.playerFrame.iconImage = SingleClient.getImage("/example/icon.jpg")
    singleClient.playerFrame.isVisible = true
    singleClient.changeFrameSize(800, 800)
    singleClient.playerFrame.pack()

    cameraLayers.addDebugObject(DebugObject(mutableMapOf(Pair("Engine Version", singleClient.version))))
    cameraLayers.addDebugObject(player)
    singleClient.setCameraLayers(cameraLayers)
    singleClient.addComponents()
    player.addListenerForTechOptions { singleClient.playerFrame.repaint() }
    singleClient.startFpsCounter()
}
