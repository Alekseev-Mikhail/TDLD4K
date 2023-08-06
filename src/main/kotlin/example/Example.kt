package example

import example.controller.KeyboardController
import example.controller.MouseController
import example.gui.EscapeGUI
import example.gui.MainGUI
import tdld4k.Client
import tdld4k.camera.handler.Key.KEY_A
import tdld4k.camera.handler.Key.KEY_D
import tdld4k.camera.handler.Key.KEY_S
import tdld4k.camera.handler.Key.KEY_W
import tdld4k.util.BLACK
import tdld4k.util.Cursor
import tdld4k.util.Paint
import tdld4k.util.geometry.PointD
import tdld4k.util.geometry.Rectangle
import tdld4k.world.AABBTile
import tdld4k.world.FullTile
import tdld4k.world.World

fun main() {
    val map = "  1e1" +
        "    1" +
        "1 4 1" +
        "2   1" +
        "2 3 1" +
        "1   1" +
        "111 1" +
        "1   1" +
        "2   1" +
        "2   1" +
        "2   1" +
        "1   1" +
        "2   1" +
        "1   1" +
        "11111"
    val mapWidth = 5
    val tileSize = 2.0
    val quality = 50.0
    val airCode = ' '
    val errorTile = Paint(3, 165, 252)
    val tileTypes = mapOf(
        '1' to FullTile(Paint(0, 0, 0)),
        '2' to FullTile(Paint(255, 21, 0)),
        '3' to AABBTile(
            Paint(0, 255, 42),
            listOf(
                Rectangle(PointD(0.0, 0.0), PointD(0.1, tileSize)),
                Rectangle(PointD(0.0, tileSize - 0.1), PointD(tileSize, tileSize)),
                Rectangle(PointD(tileSize - 0.1, tileSize / 2), PointD(tileSize, tileSize)),
                Rectangle(
                    PointD(tileSize / 2 - 0.1, tileSize / 2 - 0.1),
                    PointD(tileSize / 2 + 0.1, tileSize / 2 + 0.1),
                ),
            ),
        ),
        '4' to AABBTile(
            Paint(229, 255, 0),
            PointD(tileSize / 4, tileSize / 4),
            PointD(tileSize * 0.75, tileSize * 0.75),
        ),
    )
    val world = World(map, mapWidth, tileTypes, tileSize, quality, airCode, errorTile)
    world.outOfWorldPaint = BLACK

    val player = Player(
        tileSize + tileSize / 2,
        tileSize + tileSize / 2,
        0.5,
        0.05,
        0.05,
        0.001,
    )
    player.horizontalDirection = 90.0
    player.verticalDirection = 1.0
    val settings = Settings(
        75.0,
        quality,
        400.0,
        KEY_W,
        KEY_S,
        KEY_A,
        KEY_D,
    )
    val client = Client()

    val happyCursor = Cursor("example/cursors/happy_cursor.png")
    val sadCursor = Cursor("example/cursors/sad_cursor.png")

    val mainGUI = MainGUI(world, player, settings)
    val escapeGUI = EscapeGUI()
    val keyboardController = KeyboardController(world, player, settings, mainGUI, escapeGUI)
    val mouseController = MouseController(player, escapeGUI, happyCursor, sadCursor)

    client.createCamera { window ->
        window.content = mainGUI
        window.setIcon("example/icon.jpg")
        window.createCursor(happyCursor, sadCursor)
        window.cursor = happyCursor
        keyboardController.setCallback(window)
        mouseController.setCallback(window)
    }
//    val cameraLayers = Menus(
//        client,
//        player,
//        Color(82, 82, 82, 190),
//        8,
//        Font("debug menu", PLAIN, 25),
//        Color(240, 240, 240, 220),
//        Point(5, 5),
//    )

//    val mouseMove = MouseMove(isRobot = false, isFreezeMove = false)
//    val keyboardController = KeyboardController(
//        world,
//        VK_W,
//        VK_S,
//        VK_A,
//        VK_D,
//        client,
//        player,
//        cameraLayers,
//        mouseMove,
//    )
//    val autoEscape = AutoEscape(keyboardController)
//    val mouseController = MouseController(client, player, mouseMove)
//    val firstCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//        Client.getImage("/example/happy_cursor.png"),
//        Point(0, 0),
//        "first custom cursor",
//    )
//    val secondCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
//        Client.getImage("/example/sad_cursor.png"),
//        Point(0, 0),
//        "second custom cursor",
//    )
//
//    mouseController.firstCustomCursor = firstCustomCursor
//    mouseController.secondCustomCursor = secondCustomCursor
//    client.setCurrentCursor(firstCustomCursor)

//    client.initCamera()
//    client.playerFrame.addKeyListener(keyboardController)
//    client.playerFrame.addMouseMotionListener(mouseController)
//    client.playerFrame.addFocusListener(autoEscape)
//    client.playerFrame.defaultCloseOperation = EXIT_ON_CLOSE

//    client.setInvisibleCursor()
//    client.playerFrame.title = "Example"
//    client.playerFrame.iconImage = Client.getImage("/example/icon.jpg")
//    client.playerFrame.isVisible = true
//    client.changeFrameSize(800, 800)
//    client.playerFrame.pack()

//    cameraLayers.addDebugObject(DebugObject(mutableMapOf(Pair("Engine Version", client.version))))
//    cameraLayers.addDebugObject(player)
//    client.setCameraLayers(cameraLayers)
//    client.addComponents()
//    player.addListenerForTechOptions { client.playerFrame.repaint() }
//    client.startFpsCounter()
}

// fun getImage(name: String): Image = ImageIcon(Client::class.java.getResource(name)).image
