package example

import tdld4k.Client
import tdld4k.controllers.MouseMove
import tdld4k.debug.DebugObject
import tdld4k.math.Vector
import tdld4k.player.PlayerHeightLimits.MID_HEIGHT
import tdld4k.world.World
import tdld4k.world.fromAABBTile
import tdld4k.world.fromFullTile
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Color.BLUE
import java.awt.Color.CYAN
import java.awt.Color.GRAY
import java.awt.Color.YELLOW
import java.awt.Font
import java.awt.Font.PLAIN
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.KeyEvent.VK_A
import java.awt.event.KeyEvent.VK_D
import java.awt.event.KeyEvent.VK_S
import java.awt.event.KeyEvent.VK_W
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun main() {
    val map = "11 e1" +
        "1   1" +
        "1 2 1" +
        "1   1" +
        "1 3 1" +
        "1   1" +
        "111 1" +
        "1   1" +
        "1   1" +
        "1   1" +
        "1   1" +
        "1   1" +
        "1   1" +
        "1   1" +
        "11111"
    val mapWidth = 5
    val tileSize = 3.0
    val quality = 50.0
    val airCode = ' '
    val errorTile = fromFullTile(BLUE)
    val tileTypes = mapOf(
        '1' to fromFullTile(GRAY),
        '2' to fromAABBTile(YELLOW, Vector(tileSize / 4, tileSize / 4), Vector(tileSize * 0.75, tileSize * 0.75)),
        '3' to fromAABBTile(CYAN, Vector(0.0, 0.0), Vector(0.1, tileSize)),
    )
    val world = World(map, mapWidth, tileSize, quality, tileTypes, airCode, errorTile)
    world.outOfWorldTile = fromFullTile(BLACK)

    val player = ExamplePlayer(
        tileSize + tileSize / 2,
        MID_HEIGHT.value,
        tileSize + tileSize / 2,
        270.0,
        1.0,
        75.0,
        quality,
        400.0,
        0.05,
        0.2,
        0.005,
        60,
        isFreezeMovement = false,
        isFreezeRotation = false,
    )

    val client = Client(world, player)
    val cameraLayers = Menus(
        client,
        player,
        Color(82, 82, 82, 190),
        8,
        Font("debug menu", PLAIN, 25),
        Color(240, 240, 240, 220),
        Point(5, 5),
    )
    val mouseMove = MouseMove(isRobot = false, isFreezeMove = false)
    val keyboardController = KeyboardController(
        world,
        VK_W,
        VK_S,
        VK_A,
        VK_D,
        client,
        player,
        cameraLayers,
        mouseMove,
    )
    val autoEscape = AutoEscape(keyboardController)
    val mouseController = MouseController(client, player, mouseMove)
    val firstCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        Client.getImage("/example/cursor.png"),
        Point(0, 0),
        "first custom cursor",
    )
    val secondCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        Client.getImage("/example/cursor1.png"),
        Point(0, 0),
        "second custom cursor",
    )

    mouseController.firstCustomCursor = firstCustomCursor
    mouseController.secondCustomCursor = secondCustomCursor
    client.setCurrentCursor(firstCustomCursor)

    client.setCamera()
    client.playerFrame.addKeyListener(keyboardController)
    client.playerFrame.addMouseMotionListener(mouseController)
    client.playerFrame.addFocusListener(autoEscape)
    client.playerFrame.defaultCloseOperation = EXIT_ON_CLOSE

    client.setInvisibleCursor()
    client.playerFrame.title = "Example"
    client.playerFrame.iconImage = Client.getImage("/example/icon.jpg")
    client.playerFrame.isVisible = true
    client.changeFrameSize(800, 800)
    client.playerFrame.pack()

    cameraLayers.addDebugObject(DebugObject(mutableMapOf(Pair("Engine Version", client.version))))
    cameraLayers.addDebugObject(player)
    client.setCameraLayers(cameraLayers)
    client.addComponents()
    player.addListenerForTechOptions { client.playerFrame.repaint() }
    client.startFpsCounter()
}
